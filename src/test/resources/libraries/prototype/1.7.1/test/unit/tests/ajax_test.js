var extendDefault = function(options) {
  return Object.extend({
    asynchronous: false,
    method: 'get',
    onException: function(r, e) { throw e; }
  }, options);
};

new Test.Unit.Runner({
  setup: function() {
    $('content').update('');
    $('content2').update('');
  },
  
  teardown: function() {
    // hack to cleanup responders
    Ajax.Responders.responders = [Ajax.Responders.responders[0]];
  },
  
  testSynchronousRequest: function() {
    this.assertEqual("", $("content").innerHTML);
    
    this.assertEqual(0, Ajax.activeRequestCount);
    new Ajax.Request("../fixtures/hello.js", {
      asynchronous: false,
      method: 'GET',
      evalJS: 'force'
    });
    this.assertEqual(0, Ajax.activeRequestCount);
    
    var h2 = $("content").firstChild;
    this.assertEqual("Hello world!", h2.innerHTML);
  },
  
  testAsynchronousRequest: function() {
    this.assertEqual("", $("content").innerHTML);
    
    new Ajax.Request("../fixtures/hello.js", {
      asynchronous: true,
      method: 'get',
      evalJS: 'force'
    });
    this.wait(1000, function() {
      var h2 = $("content").firstChild;
      this.assertEqual("Hello world!", h2.innerHTML);
    });
  },
  
  testUpdater: function() {
    this.assertEqual("", $("content").innerHTML);
    
    new Ajax.Updater("content", "../fixtures/content.html", { method:'get' });
    
    this.wait(1000, function() {
      this.assertEqual(sentence, $("content").innerHTML.strip().toLowerCase());
      
      $('content').update('');
      this.assertEqual("", $("content").innerHTML);
       
      new Ajax.Updater({ success:"content", failure:"content2" },
        "../fixtures/content.html", { method:'get', parameters:{ pet:'monkey' } });
      
      new Ajax.Updater("", "../fixtures/content.html", { method:'get', parameters:"pet=monkey" });
      
      this.wait(1000, function() {
        this.assertEqual(sentence, $("content").innerHTML.strip().toLowerCase());
        this.assertEqual("", $("content2").innerHTML);
      });
    }); 
  },
  
  testUpdaterWithInsertion: function() {
    $('content').update();
    new Ajax.Updater("content", "../fixtures/content.html", { method:'get', insertion: Insertion.Top });
    this.wait(1000, function() {
      this.assertEqual(sentence, $("content").innerHTML.strip().toLowerCase());
      $('content').update();
      new Ajax.Updater("content", "../fixtures/content.html", { method:'get', insertion: 'bottom' });      
      this.wait(1000, function() {
        this.assertEqual(sentence, $("content").innerHTML.strip().toLowerCase());
        
        $('content').update();
        new Ajax.Updater("content", "../fixtures/content.html", { method:'get', insertion: 'after' });      
        this.wait(1000, function() {
          this.assertEqual('five dozen', $("content").next().innerHTML.strip().toLowerCase());
        });
      });
    });
  },
  
  testUpdaterOptions: function() {
    var options = {
      method: 'get',
      asynchronous: false,
      evalJS: 'force',
      onComplete: Prototype.emptyFunction
    }
    var request = new Ajax.Updater("content", "../fixtures/hello.js", options);
    request.options.onComplete = Prototype.emptyFunction;
    this.assertIdentical(Prototype.emptyFunction, options.onComplete);
  },
  
  testResponders: function(){
    // check for internal responder
    this.assertEqual(1, Ajax.Responders.responders.length);
    
    var dummyResponder = {
      onComplete: Prototype.emptyFunction
    };
    
    Ajax.Responders.register(dummyResponder);
    this.assertEqual(2, Ajax.Responders.responders.length);
    
    // don't add twice
    Ajax.Responders.register(dummyResponder);
    this.assertEqual(2, Ajax.Responders.responders.length);
    
    Ajax.Responders.unregister(dummyResponder);
    this.assertEqual(1, Ajax.Responders.responders.length);
    
    var responder = {
      onCreate:   function(req){ responderCounter++ },
      onLoading:  function(req){ responderCounter++ },
      onComplete: function(req){ responderCounter++ }
    };
    Ajax.Responders.register(responder);
    
    this.assertEqual(0, responderCounter);
    this.assertEqual(0, Ajax.activeRequestCount);
    new Ajax.Request("../fixtures/content.html", { method:'get', parameters:"pet=monkey" });
    this.assertEqual(1, responderCounter);
    this.assertEqual(1, Ajax.activeRequestCount);
    
    this.wait(1000,function() {
      this.assertEqual(3, responderCounter);
      this.assertEqual(0, Ajax.activeRequestCount);
    });
  },
  
  testEvalResponseShouldBeCalledBeforeOnComplete: function() {
    if (this.isRunningFromRake) {
      this.assertEqual("", $("content").innerHTML);
    
      this.assertEqual(0, Ajax.activeRequestCount);
      new Ajax.Request("../fixtures/hello.js", extendDefault({
        onComplete: function(response) { this.assertNotEqual("", $("content").innerHTML) }.bind(this)
      }));
      this.assertEqual(0, Ajax.activeRequestCount);
    
      var h2 = $("content").firstChild;
      this.assertEqual("Hello world!", h2.innerHTML);
    } else {
      this.info(message);
    }
  },
  
  testContentTypeSetForSimulatedVerbs: function() {
    if (this.isRunningFromRake) {
      new Ajax.Request('/inspect', extendDefault({
        method: 'put',
        contentType: 'application/bogus',
        onComplete: function(response) {
          this.assertEqual('application/bogus; charset=UTF-8', response.responseJSON.headers['content-type']);
        }.bind(this)
      }));
    } else {
      this.info(message);
    }
  },
  
  testOnCreateCallback: function() {
    new Ajax.Request("../fixtures/content.html", extendDefault({
      onCreate: function(transport) { this.assertEqual(0, transport.readyState) }.bind(this),
      onComplete: function(transport) { this.assertNotEqual(0, transport.readyState) }.bind(this)
    }));
  },
  
  testEvalJS: function() {
    if (this.isRunningFromRake) {
      
      $('content').update();
      new Ajax.Request("/response", extendDefault({
        parameters: Fixtures.js,
        onComplete: function(transport) { 
          var h2 = $("content").firstChild;
          this.assertEqual("Hello world!", h2.innerHTML);
        }.bind(this)
      }));
      
      $('content').update();
      new Ajax.Request("/response", extendDefault({
        evalJS: false,
        parameters: Fixtures.js,
        onComplete: function(transport) { 
          this.assertEqual("", $("content").innerHTML);
        }.bind(this)
      }));
    } else {
      this.info(message);
    }
    
    $('content').update();
    new Ajax.Request("../fixtures/hello.js", extendDefault({
      evalJS: 'force',
      onComplete: function(transport) { 
        var h2 = $("content").firstChild;
        this.assertEqual("Hello world!", h2.innerHTML);
      }.bind(this)
    }));
  },

  testCallbacks: function() {
    var options = extendDefault({
      onCreate: function(transport) { this.assertInstanceOf(Ajax.Response, transport) }.bind(this)
    });
    
    Ajax.Request.Events.each(function(state){
      options['on' + state] = options.onCreate;
    });

    new Ajax.Request("../fixtures/content.html", options);
  },

  testResponseText: function() {
    new Ajax.Request("../fixtures/empty.html", extendDefault({
      onComplete: function(transport) { this.assertEqual('', transport.responseText) }.bind(this)
    }));
    
    new Ajax.Request("../fixtures/content.html", extendDefault({
      onComplete: function(transport) { this.assertEqual(sentence, transport.responseText.toLowerCase()) }.bind(this)
    }));
  },
  
  testResponseXML: function() {
    if (this.isRunningFromRake) {
      new Ajax.Request("/response", extendDefault({
        parameters: Fixtures.xml,
        onComplete: function(transport) { 
          this.assertEqual('foo', transport.responseXML.getElementsByTagName('name')[0].getAttribute('attr'))
        }.bind(this)
      }));
    } else {
      this.info(message);
    }
  },
      
  testResponseJSON: function() {
    if (this.isRunningFromRake) {
      new Ajax.Request("/response", extendDefault({
        parameters: Fixtures.json,
        onComplete: function(transport) { this.assertEqual(123, transport.responseJSON.test) }.bind(this)
      }));
      
      new Ajax.Request("/response", extendDefault({
        parameters: {
          'Content-Length': 0,
          'Content-Type': 'application/json'
        },
        onComplete: function(transport) { this.assertNull(transport.responseJSON) }.bind(this)
      }));
      
      new Ajax.Request("/response", extendDefault({
        evalJSON: false,
        parameters: Fixtures.json,
        onComplete: function(transport) { this.assertNull(transport.responseJSON) }.bind(this)
      }));
    
      new Ajax.Request("/response", extendDefault({
        parameters: Fixtures.jsonWithoutContentType,
        onComplete: function(transport) { this.assertNull(transport.responseJSON) }.bind(this)
      }));
    
      new Ajax.Request("/response", extendDefault({
        sanitizeJSON: true,
        parameters: Fixtures.invalidJson,
        onException: function(request, error) {
          this.assertEqual('SyntaxError', error.name);
        }.bind(this)
      }));
    } else {
      this.info(message);
    }
    
    new Ajax.Request("../fixtures/data.json", extendDefault({
      evalJSON: 'force',
      onComplete: function(transport) { this.assertEqual(123, transport.responseJSON.test) }.bind(this)
    }));
  },
  
  testHeaderJSON: function() {
    if (this.isRunningFromRake) {
      new Ajax.Request("/response", extendDefault({
        parameters: Fixtures.headerJson,
        onComplete: function(transport, json) {
          this.assertEqual('hello #????', transport.headerJSON.test);
          this.assertEqual('hello #????', json.test);
        }.bind(this)
      }));
    
      new Ajax.Request("/response", extendDefault({
        onComplete: function(transport, json) { 
          this.assertNull(transport.headerJSON)
          this.assertNull(json)
        }.bind(this)
      }));
    } else {
      this.info(message);
    }
  },
  
  testGetHeader: function() {
    if (this.isRunningFromRake) {
     new Ajax.Request("/response", extendDefault({
        parameters: { 'X-TEST': 'some value' },
        onComplete: function(transport) {
          this.assertEqual('some value', transport.getHeader('X-Test'));
          this.assertNull(transport.getHeader('X-Inexistant'));
        }.bind(this)
      }));
    } else {
      this.info(message);
    }
  },
  
  testParametersCanBeHash: function() {
    if (this.isRunningFromRake) {
      new Ajax.Request("/response", extendDefault({
        parameters: $H({ "one": "two", "three": "four" }),
        onComplete: function(transport) {
          this.assertEqual("two", transport.getHeader("one"));
          this.assertEqual("four", transport.getHeader("three"));
          this.assertNull(transport.getHeader("toObject"));
        }.bind(this)
      }));
    } else {
      this.info(message);
    }
  },
  
  testParametersStringOrderIsPreserved: function() {
    if (this.isRunningFromRake) {
      new Ajax.Request("/inspect", extendDefault({
        parameters: "cool=1&bad=2&cool=3&bad=4",
        method: 'post',
        onComplete: function(transport) {
          var body_without_wart = transport.responseJSON.body.match(/((?:(?!&_=$).)*)/)[1];
          this.assertEqual("cool=1&bad=2&cool=3&bad=4", body_without_wart);
        }.bind(this)
      }));
    }
  },
  
  testIsSameOriginMethod: function() {
    var isSameOrigin = Ajax.Request.prototype.isSameOrigin;
    this.assert(isSameOrigin.call({ url: '/foo/bar.html' }), '/foo/bar.html');
    this.assert(isSameOrigin.call({ url: window.location.toString() }), window.location);
    this.assert(!isSameOrigin.call({ url: 'http://example.com' }), 'http://example.com');

    if (this.isRunningFromRake) {
      Ajax.Request.prototype.isSameOrigin = function() {
        return false
      };

      $("content").update('same origin policy');
      new Ajax.Request("/response", extendDefault({
        parameters: Fixtures.js,
        onComplete: function(transport) { 
          this.assertEqual("same origin policy", $("content").innerHTML);
        }.bind(this)
      }));

      new Ajax.Request("/response", extendDefault({
        parameters: Fixtures.invalidJson,
        onException: function(request, error) {
          this.assertEqual('SyntaxError', error.name);
        }.bind(this)
      }));

      new Ajax.Request("/response", extendDefault({
        parameters: { 'X-JSON': '{});window.attacked = true;({}' },
        onException: function(request, error) {
          this.assertEqual('SyntaxError', error.name);
        }.bind(this)
      }));

      Ajax.Request.prototype.isSameOrigin = isSameOrigin;
    } else {
      this.info(message);
    }
  }
});
