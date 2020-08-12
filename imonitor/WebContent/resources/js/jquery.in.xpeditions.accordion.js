(function($) {

    var XpAccordionMenu = function(element, options){
		//Defaults are below
		var settings = $.extend({}, $.fn.xpAccordionMenu.defaults, options);
		
		var vars = {
            thisSectionMayUseLater : 0
        };
		element = $(element);
		element.find('ul').hide();
		if(settings.expandFirst){
			element.find('ul:first').show();
		}
		element.find('li a').click(
		    function() {
		      var checkElement = $(this).next();
		      if((checkElement.is('ul')) && (checkElement.is(':visible'))) {
		        return false;
		        }
		      if((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
		    	element.find('ul:visible').slideUp('normal');
		        checkElement.slideDown('normal');
		        return false;
		        }
		      }
		    );
	};
    $.fn.xpAccordionMenu = function(options) {
    
        return this.each(function(){
            var element = $(this);
            // Return early if this element already has a plugin instance
            if (element.data('xpAccordionMenu')) return;
            // Pass options to plugin constructor
            var xpAccordionMenu = new XpAccordionMenu(this, options);
            // Store plugin object in this element's data
            element.data('xpAccordionMenu', xpAccordionMenu);
        });

	};
	
	//Default settings
	$.fn.xpAccordionMenu.defaults = {
		expandFirst: true
	};
	
	$.fn._reverse = [].reverse;
	
})(jQuery);