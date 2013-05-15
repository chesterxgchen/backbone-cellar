# spraydust

In this forked project, we added the followings:

 1) using spray rest api as the backend  (http://spray.io/)
 2) replace template with dust template (http://linkedin.github.io/dustjs/)

The sub-project is loaded the "spraydust" directory

##Build

I am using sbt to build, sbt can be found at http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html

In this project I am using several snapshots builds:

spray -- spray-1.1-M8-SNAPSHOT nightly build. to build spray

<pre><code>
git clone https://github.com/spray/spray.git
cd spray
sbt publish-local
</code></pre>

You could use spray-1.1-M7 build, which is already published. But the syntax will need to be changed.
Also, since M8 is going to be released soon (possibly within a month, May or June 2013),
it's better use M8 version.

spa  -- scalar persistence API (https://github.com/chesterxgchen/spa), this dependency is not published, so I included in the project lib directory

sbt-dustjs plugin -- the original sbt-dustjs plugin (https://github.com/timperrett/sbt-dustjs) only works for sbt 0.11 (scala 2.9.1),
                     to use the sbt 0.12 you need to use the forked version at https://github.com/chesterxgchen/sbt-dustjs
                     since the modified version is not published, you may have to build yourself.

<pre><code>
git clone https://github.com/chesterxgchen/sbt-dustjs.git
cd sbt-dustjs
sbt publish-local
</code></pre>

## Database access layer.

I use spa for the mysql database operations. To make things easier, I changed the
description column from blob to VARCHAR (4000). The database persistence codes are in WineDAO


## Dust template

I use sbt-dustjs plugin to compile the dust template into javascripts and use sbt-js to concatenate several java script files into one fine,
which is included in the index.html. Even though the dust can be compiled via node.js (such as linkedIn dustc and duster.js),
I felt the sbt-dustjs would easier for automated build without introduce Node infrastructure.

sbt-dustjs expects the dust template located at src/main/dust directory. I have configure the compiled js dust templates into src/main/resources/web/js/dust
each template uses the file base name (filename without extension) as the registered name, which be used for rendering
to concatenate the dust templates into one single template, I have used spraydust.jsm manifest file. to generate the js files:

<pre><code>
sbt dust js
</code></pre>

then you need to manually copy the spraydust.js to the desired location: for example

<pre><code>
cp target/scala-2.10/resource_managed/main/resources/web/js/dust/spraydust.js src/main/resources/web/js/dust/spraydust.js
</code></pre>

There are some issue at the moment to configure sbt-js to out to the  src/main/resources/web/js/dust/spraydust.js directly.

## Other changes

In index.html we need to add

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


Since we pre-compiled the dust templates into java scripts and directly included compiled java scripts into the index.html, 
there is no need to dynamically load the templates as the original code did. 

There is no need for dynamic on-damand dust compilation either. 

In the Main.js, the startup code simply becomes

<pre><code>
app = new AppRouter();
Backbone.history.start();
</code></pre>





## API layer:
(to be completed)

