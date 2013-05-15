window.AboutView = Backbone.View.extend({

    initialize:function () {
        this.render();
    },

    render:function () {
      //  $(this.el).html(this.template());
          $(this.el).html(this.dustTemplate("aboutview"))
        return this;
    }

});