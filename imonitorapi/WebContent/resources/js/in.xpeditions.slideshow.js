/*!
 * Copyright © 2012 iMonitor Solutions India Private Limited
 */
(function($) {

    var XpHorizontalSlider = function(element, options){
		//Defaults are below
		var settings = $.extend({}, $.fn.xpHorizontalSlider.defaults, options);
		element = $(element);
		var vars = {
            hCurrentPosition : 0,
			hSlides: element.find("." + settings.hSlideClass),
			hCountOfSlides: element.find("." + settings.hSlideClass).length,
			stopAutoSliding: false,
			hSliderWrapperId: element.attr('id') + Math.floor(Math.random()*1001),
			hOuterWrapperId: element.attr('id') + 'OuterWraper' + Math.floor(Math.random()*1001),
        };
		// Remove scrollbar in JS
		element.css('overflow', 'hidden');
		element.height(settings.hSlideHeight);
		element.addClass(settings.hslidesContainerClass);

		  // Wrap all .slides with #slideInner div
		  vars.hSlides
			.wrapAll('<div id="' + vars.hSliderWrapperId + '"></div>')
			// Float left to display horizontally, readjust .slides width
			.css({
			  'float' : 'left',
			  'width' : settings.hSlideWidth
			})
			.height(settings.hSlideHeight);
	
		  // Set #slideWrapper width equal to total width of all slides
		  $('#' + vars.hSliderWrapperId).css('width', settings.hSlideWidth * vars.hCountOfSlides);
		  $('#' + vars.hSliderWrapperId).height(settings.hSlideHeight);
		  
		  element.wrapAll('<div id="' + vars.hOuterWrapperId + '"></div>')
		  $('#' + vars.hOuterWrapperId).addClass(settings.outerWrapperClass);
		  $('#' + vars.hOuterWrapperId).height(settings.hSlideHeight);
		  $('#' + vars.hOuterWrapperId).bind('mouseover', function(){
			vars.stopAutoSliding = true;
		  });
		  $('#' + vars.hOuterWrapperId).bind('mouseout', function(){
			vars.stopAutoSliding = false;
		  });
		  // Insert controls in the DOM
		$('#' + vars.hOuterWrapperId)
			.prepend('<span class="' + settings.hslidesControlClass + ' ' + settings.hslidesLeftControlClass + '">Clicking moves left</span>')
			.append('<span class="' + settings.hslidesControlClass + ' ' + settings.hslidesRightControlClass + '">Clicking moves right</span>');
		$('.' + settings.hslidesControlClass).height(settings.hSlideHeight);
		// Hide left arrow control on first load
		manageControls(vars.hCurrentPosition);
  
		// Create event listeners for .controls clicks
		$('#' + vars.hOuterWrapperId).find("." + settings.hslidesControlClass)
			.bind('click', function(){
				// Determine new position
				vars.hCurrentPosition = ($(this).hasClass(settings.hslidesRightControlClass)) ? vars.hCurrentPosition+1 : vars.hCurrentPosition-1;
				// Hide / show controls
				manageControls();
				// Move slideInner using margin-left
				moveSlides();
				
		});
		// Moving the slider the current position.
		function moveSlides(){
			$('#' + vars.hSliderWrapperId).animate({
			  'marginLeft' : settings.hSlideWidth*(-vars.hCurrentPosition)
			});
		}
		// manageControls: Hides and Shows controls depending on vars.hCurrentPosition
		function manageControls(){
			// Hide left arrow if position is first slide
			if(vars.hCurrentPosition==0){
				$('#' + vars.hOuterWrapperId).find("." + settings.hslidesLeftControlClass).hide();
			} else{
				$('#' + vars.hOuterWrapperId).find('.' + settings.hslidesLeftControlClass).show();
			}
			// Hide right arrow if position is last slide
			if(vars.hCurrentPosition==vars.hCountOfSlides - settings.countAtATime){
				$('#' + vars.hOuterWrapperId).find('.' + settings.hslidesRightControlClass).hide();
			} else{
				$('#' + vars.hOuterWrapperId).find('.' + settings.hslidesRightControlClass).show();
			}
		}
		
		function autoSlide(){
		//setTimeout (function,timeout); 
			if(!vars.stopAutoSliding){
				if(vars.hCurrentPosition==vars.hCountOfSlides-1){
					vars.hCurrentPosition = 0;
				} else{
					vars.hCurrentPosition++;
				}
				manageControls();
				moveSlides();
			}
			setTimeout (autoSlide,settings.slideWeightTime);
		}
		// Start the auto slide
		if(settings.hAutoSliding){		
			setTimeout (autoSlide,settings.slideWeightTime);
		}
	// Hide the window if not auto open.
		var _showSlider = function(){
			$('#' + vars.hOuterWrapperId).show();
		};
		var _hideSlider = function(){
			$('#' + vars.hOuterWrapperId).hide();
		};
		if(!settings.autoOpen){
			_hideSlider();
		}
		return {
				showSlider : _showSlider,
				hideSlider : _hideSlider
		};
	};
	
    $.fn.xpHorizontalSlider = function(options) {    
    	//var sliders = [];
        //this.each(function(){
            var element = $(this);
            // Return early if this element already has a plugin instance
            if (element.data('xpHorizontalSlider')) {
            	//$.merge(sliders, [element.data('xpHorizontalSlider')]);
            	return element.data('xpHorizontalSlider');
            }
            // Pass options to plugin constructor
            var xpHorizontalSlide = new XpHorizontalSlider(this, options);
            // Store plugin object in this element's data
            element.data('xpHorizontalSlider', xpHorizontalSlide);
            return xpHorizontalSlide;
        	//$.merge(sliders, [element.data('xpHorizontalSlider')]);
        //});
        //return sliders.length;
	};
	
	//Default settings
	$.fn.xpHorizontalSlider.defaults = {
			hSlideClass: "hslide",
			outerWrapperClass: "hSlideshow",
			hslidesContainerClass: "hslidesContainer",
			hslidesControlClass: "control",
			hslidesLeftControlClass: "leftControl",
			hslidesRightControlClass: "rightControl",
			hAutoSliding: true,
			hSlideWidth: 600,
			hSlideHeight: 169,
			slideWeightTime: 3000,
			countAtATime: 4,
			autoOpen: true
	};
	
	$.fn._reverse = [].reverse;
	
})(jQuery);