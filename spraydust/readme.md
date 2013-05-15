
Original markdown

# Wine Cellar Sample Application with Backbone.js and Twitter Bootstrap #

"Backbone Cellar" is a sample CRUD application built with Backbone.js and Twitter Bootstrap. The application allows you to browse through a list of wines, as well as add, update, and delete wines.

This application is further documented [here](http://coenraets.org/blog).

The application is also hosted online. You can test it [here](http://coenraets.org/backbone-cellar/bootstrap).

The application is provided with both an in-memory datastore (default) and a RESTful back-end implemented in PHP (see the /api directory).

The in-memory datastore allows you to experience the app without setting up a back-end. All the changes you make to the data will be lost the next time you access the app or hit Refresh.
To use the app with the persistent RESTful back-end, set up the database as documented below and comment out the memorystore.js script import in index.html.

## Database Set Up (Optional): ##

1. Create a MySQL database name "cellar".
2. Execute cellar.sql to create and populate the "wine" table:

	mysql cellar -uroot < cellar.sql

Your feedback is appreciated. Please post your questions and comments in the blog post referenced above.


# updated readme.markdown

This updated project uses the original backbone.js + bootstrap + spray + dust.js

 1) replacing PHP api with spray rest api
 2) replace underscore template with dust template

The sub-project is loaded the "spraydust" directory


Build

 I am using sbt to build, you need to install sbt

 http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html

I have to use several snapshot builds for this project:

spray -- spray-1.1-M8-shapshot nightly build. to build spray

<pre><code>
git clone https://github.com/spray/spray.git
cd spray
sbt publish-local
</code></pre>

spa  -- https://github.com/chesterxgchen/spa (included in the project lib directory)


sbt-dustjs plugin -- the original sbt-dustjs plugin (https://github.com/timperrett/sbt-dustjs) only works for sbt 0.11 (scala 2.9.1),
                     to use the sbt 0.12 you need to use the forked version at https://github.com/chesterxgchen/sbt-dustjs

<pre><code>
git clone https://github.com/chesterxgchen/sbt-dustjs.git
cd sbt-dustjs
sbt publish-local

</code></pre>

Database access layer.

I use SPA (scala persistence API) for the mysql database query. To make things easier, I changed the
description column from blob to VARCHAR (4000). The database persistence codes are in WineDAO

Dust template

I use sbt-dustjs plugin to compile the dust template into javascripts and use sbt-js to concatenate several java script files into one fine, which is included in the index.html

sbt-dustjs expects the dust template located at src/main/dust directory.

I have configure the compiled js dust templates into src/main/resources/web/js/dust

each template uses the file base name (filename without extension) as the registered name, which be used for rendering

to concatenate the dust templates into one single template, I have used spraydust.jsm manifest file.

to generate the js files:

<pre><code>

sbt dust js

</code></pre>

then you need to manually cp the spraydust.js

target/scala-2.10/resource_managed/main/resources/web/js/dust/spraydust.js
to
src/main/resources/web/js/dust/spraydust.js

Even I can configure sbt-js to out to the  src/main/resources/web/js/dust/spraydust.js but it's better you don't do this:

this is how would I do this, but if you type sbt clean, all src code will be deleted.that's why I am manually do this.

//don't do this, this will cause all src code being deleted with sbt clean
(resourceManaged in (Compile, JsKeys.js)) <<= (sourceDirectory in Compile)( _ /"." )

Other changes:

In Index.html we need to add

dust-full-1.2.4.min.js
spraydust.js

and remove
memorystore.js

in model.js, we change the default picture and year

<pre><code>
 defaults: {
        id: null,
        name: "",
        grapes: "",
        country: "USA",
        region: "California",
        year: "2012",
        description: "",
        picture: "generic.jpg"
    }

</code></pre>

 In main.js I added two other functions

<pre><code>

  Backbone.View.prototype.close = function () {
      console.log('Closing view ' + this);
      if (this.beforeClose) {
          this.beforeClose();
      }
      this.remove();
      this.unbind();
  };

  Backbone.View.prototype.dustTemplate = function(name, data) {
              var result;
              dust.render(name, data, function(err, res) {
                 result = res;
              });
              return result;
  };

</code></pre>

This first function helps close, the 2nd one used for Dust template rendering. To change from underscore template to dust template
is really easy, here is the change for "winewiew" template

<pre><code>

        // $(this.el).html(this.template(this.model.toJSON()));
         $(this.el).html(this.dustTemplate("wineview",this.model.toJSON() ));

</code></pre>


API layer:

