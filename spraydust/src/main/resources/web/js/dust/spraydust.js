(function(){function d(e,b){return e.write('<div class="row"><div class="span12"><img class="about-icon" src="img/download.png" width="50"><h3>Download the source code</h3><p>The source code for this application is available in this repository on GitHub.</p></div></div><br/><div class="row"><div class="span12"><img class="about-icon" src="img/discuss.png" width="50" style="float: left;"><h3>Comments and questions</h3><p>I love to hear your feedback. Post your questions and comments on the blog post associated with thisapplication.</p></div></div><br/><div class="row"><div class="span12"><img class="about-icon" src="img/twitter.png" width="50" style="float: left;"><h3>Follow me on Twitter</h3><p><a href="http://twitter.com/ccoenraets">@ccoenraets</a></p></div></div><br/><div class="row"><div class="span12"><img class="about-icon" src="img/blog.png" width="50" style="float: left;"><h3>Check out my blog</h3><p><a href="http://coenraets.org">http://coenraets.org</a></p></div></div>')}
dust.register("aboutview",d);return d})();
(function(){function d(e,b){return e.write('<div class="navbar navbar-fixed-top" xmlns="http://www.w3.org/1999/html"><div class="navbar-inner"><div class="container"><a class="brand" href="#">Backbone Cellar</a><div class="nav-collapse"><ul class="nav"><li class="add-menu"><a href="#wines/add"><i class="icon-edit icon-white"></i> Add Wine</a></li></ul><ul class="nav pull-right"><li class="about-menu"><a href="#about">About</a></li><li class="divider-vertical"></li><li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Resources <b class="caret"></b></a><ul class="dropdown-menu"><li><a href="http://documentcloud.github.com/backbone/" target="_blank">Backbone.js</a></li><li><a href="http://twitter.github.com/bootstrap/" target="_blank">Twitter Bootstrap</a></li><li><a href="http://documentcloud.github.com/underscore/" target="_blank">Underscore.js</a></li><li><a href="http://linkedin.github.io/dustjs/" target="_blank">Dust.js</a></li><li class="divider"></li><li class="nav-header">This App</li><li><a href="#" target="_blank">GitHub Repository</a></li><li><a href="http://coenraets.org" target="_blank">Author\'s Blog</a></li></ul></li></ul></div></div></div></div>')}dust.register("headerview",
d);return d})();
(function(){function d(e,b){return e.write('<a href="#wines/').reference(b.get("id"),b,"h").write('" class="thumbnail plain"><img src="pics/').reference(b.get("picture"),b,"h").write('" height="150" width="125" alt=""><h5>').reference(b.get("name"),b,"h").write("</h5><p>").reference(b.get("year"),b,"h").write(" ").reference(b.get("grapes"),b,"h").write(' <br/><i class="icon-globe"></i> ').reference(b.get("region"),b,"h").write(", ").reference(b.get("country"),b,"h").write("</p></a>")}dust.register("winelistitemview",
d);return d})();
(function(){function d(c,a){return c.write('<div class="row"><form class="form-horizontal span12"><fieldset><legend>Wine Details</legend><br/><div class="row"><div class="span9"><div class="control-group"><label for="wineId" class="control-label">Id:</label><div class="controls"><input id="wineId" name="id" type="text" value="').reference(a.get("id"),a,"h").write('" class="span1"disabled/></div></div><div class="control-group"><label for="name" class="control-label">Name:</label><div class="controls"><input type="text" id="name" name="name" value="').reference(a.get("name"),a,
"h").write('"/><span class="help-inline"></span></div></div><div class="control-group"><label for="grapes" class="control-label">Grapes:</label><div class="controls"><input type="text" id="grapes" name="grapes" value="').reference(a.get("grapes"),a,"h").write('"/><span class="help-inline"></span></div></div><div class="control-group"><label for="country" class="control-label">Country:</label><div class="controls"><input type="text" id="country" name="country" value="').reference(a.get("country"),
a,"h").write('"/><span class="help-inline"></span></div></div><div class="control-group"><label for="region" class="control-label">Region:</label><div class="controls"><input type="text" id="region" name="region" value="').reference(a.get("region"),a,"h").write('"/></div></div><div class="control-group"><label for="year" class="control-label">Year:</label><div class="controls"><select class="span2" id="year" name="year" value="').reference(a.get("year"),a,"h").write('"><option value="2012"').helper("eq",
a,{"block":e},{"key":a.get("year"),"value":"2012"}).write(' >2012</option><option value="2011"').helper("eq",a,{"block":b},{"key":a.get("year"),"value":"2011"}).write(' >2011</option><option value="2010"').helper("eq",a,{"block":f},{"key":a.get("year"),"value":"2010"}).write(' >2010</option><option value="2009"').helper("eq",a,{"block":g},{"key":a.get("year"),"value":"2009"}).write(' >2009</option><option value="2008"').helper("eq",a,{"block":h},{"key":a.get("year"),"value":"2008"}).write(' >2008</option><option value="2007"').helper("eq",
a,{"block":i},{"key":a.get("year"),"value":"2007"}).write(' >2007</option></select></div></div><div class="control-group"><label for="description" class="control-label">Notes:</label><div class="controls"><textarea id="description" name="description" class="span6" rows="6">').reference(a.get("description"),a,"h").write('</textarea></div></div></div><div class="span3"><div class="well"><p><img id="picture" width="180" src="pics/').reference(a.get("picture"),a,"h").write('"/></p><p>To change the picture, drag a new picture from your file system onto the box above.</p></div></div></div></fieldset><div class="form-actions"><a href="#" class="btn btn-primary save">Save</a><a href="#" class="btn delete">Delete</a></div></form></div><div class="row status-bar"><div class="span12"><div class="alert alert-success" style="display: none"><b>Success!</b> Wine saved successfully</div></div></div>')}
function e(c,a){return c.write('  selected="true"')}function b(c,a){return c.write('  selected="true"')}function f(c,a){return c.write('  selected="true"')}function g(c,a){return c.write('  selected="true"')}function h(c,a){return c.write('  selected="true"')}function i(c,a){return c.write('  selected="true"')}dust.register("wineview",d);return d})();