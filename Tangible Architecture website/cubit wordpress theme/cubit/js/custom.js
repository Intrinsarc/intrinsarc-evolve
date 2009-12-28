jQuery.noConflict();


function kriesi_mainmenu(){
jQuery("#nav a, #subnav a").removeAttr('title');
jQuery(" #nav ul ").css({display: "none"}); // Opera Fix
jQuery(" #nav li").hover(function(){
		jQuery(this).find('ul:first:hidden').css({visibility: "visible",display: "none"}).show(400);
		},function(){
		jQuery(this).find('ul:first').css({visibility: "hidden"});
		});
}

///This functions checks on which subpage you are and applies the background to the main menu
function lavahelper() 
{
	jQuery("#nav .current_page_item, #nav .current_page_parent, #nav .current_page_ancestor").addClass('current').removeClass("current_page_item").removeClass("current_page_parent").removeClass("current_page_ancestor");
}




function form_validation(){
	jQuery(".empty, .email").each(function(i){
									  
				jQuery(this).bind("blur", function(){
				
				var value = jQuery(this).attr("value");
				var check_for = jQuery(this).attr("class");
				var surrounding_element = jQuery(this).parent("p");
				var template_url = jQuery("meta[name=Cube_option1]").attr('content');
				
				 jQuery.ajax({
					   type: "POST",
					   url: template_url + "/validate.php",
					   data: "value="+value+"&check_for="+check_for,
					   beforeSend:function(){
						   surrounding_element.attr("class","").addClass("ajax_loading");
						 },
					   error:function(){
						   surrounding_element.attr("class","").addClass("ajax_alert");
						 },
					   success: function(response){
						   if(response == "true"){
							surrounding_element.attr("class","").addClass("ajax_valid");
						   }else{
							surrounding_element.attr("class","").addClass("ajax_false");
						   }
					     }
						   
			 });
		 });
	});
}



function validate_all(){
	var my_error;
	jQuery(".ajax_form #send").bind("click", function(){
	my_error = false;
	jQuery(".empty, .email").each(function(i){
										   
				var value = jQuery(this).attr("value");
				var check_for = jQuery(this).attr("class");
				var surrounding_element = jQuery(this).parent("p");
				var template_url = jQuery("meta[name=Cube_option1]").attr('content');
				
				 jQuery.ajax({
					   type: "POST",
					   url: template_url + "/validate.php",
					   data: "value="+value+"&check_for="+check_for,
					   beforeSend:function(){
						   surrounding_element.attr("class","").addClass("ajax_loading");
						 },
					   error:function(){
						   surrounding_element.attr("class","").addClass("ajax_alert");
						 },
					   success: function(response){
						   if(response == "true"){
							surrounding_element.attr("class","").addClass("ajax_valid");
						   }else{
							surrounding_element.attr("class","").addClass("ajax_false");
							my_error = true;
						   }
						   if(jQuery(".empty, .email").length  == i+1){
								if(my_error == false){
									jQuery("#ajax_form").slideUp(400);
									var yourname = jQuery("#name").attr('value');
									var email = jQuery("#email").attr('value');
									var website = jQuery("#website").attr('value');
									var message = jQuery("#message").attr('value');
									var myemail = jQuery("#myemail").attr('value');
									var myblogname = jQuery("#myblogname").attr('value');
									
									jQuery.ajax({
									   type: "POST",
									   url: template_url + "/send.php",
									   data: "Send=true&yourname="+yourname+"&email="+email+"&website="+website+"&message="+message+"&myemail="+myemail+"&myblogname="+myblogname,
									   success: function(response){
									   jQuery("#ajax_response").css({display:"none"}).html(response).slideDown(400);   
										   }
												});
									
									
									} 
							}
					     }
				 });
				 
			});
	
	return false;
	});
}






jQuery(document).ready(function(){
	kriesi_mainmenu();
	lavahelper();
	jQuery("#nav").lavaLamp({ fx: "easeOutCubic", speed: 700 });
	 form_validation();
	 validate_all();

});

jQuery(window).load(function(){ // starts executing after all images have loaded to ensure best performance
	jQuery("#transition-container").toolani_transition({block_size: 80,display_for:6000}); //dont set lower then 6000 or it will produce errors
	});
	
	









/**
 * LavaLamp - A menu plugin for jQuery with cool hover effects.
 * @requires jQuery v1.1.3.1 or above
 *
 * http://gmarwaha.com/blog/?p=7
 *
 * Copyright (c) 2007 Ganeshji Marwaha (gmarwaha.com)
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 *
 * Version: 0.2.0
 * Requires Jquery 1.2.1 from version 0.2.0 onwards. 
 * For jquery 1.1.x, use version 0.1.0 of lavalamp
 */

(function(jQuery) {
jQuery.fn.lavaLamp = function(o) {
    o = jQuery.extend({ fx: "linear", speed: 500, click: function(){} }, o || {});

    return this.each(function() {
        var me = jQuery(this), noop = function(){},
            jQueryback = jQuery('<li class="back"><div class="left"></div></li>').appendTo(me),
            jQueryli = jQuery("li", this), curr = jQuery("li.current", this)[0] || false;
			
			if (curr == false){jQuery(".back").remove();return false;}

        jQueryli.not(".back").not("#nav ul li").hover(function() {
            move(this);
        }, noop);

        jQuery(this).hover(noop, function() {
            move(curr);
        });

        jQueryli.click(function(e) {
            setCurr(this);
            return o.click.apply(this, [e, this]);
        });

        setCurr(curr);

        function setCurr(el) {
            jQueryback.css({ "left": el.offsetLeft+"px", "width": el.offsetWidth+"px" });
            curr = el;
        };

        function move(el) {
            jQueryback.each(function() {
                jQuery(this).dequeue(); }
            ).animate({
                width: el.offsetWidth,
                left: el.offsetLeft
            }, o.speed, o.fx);
        };

    });
};
})(jQuery);

/*
 * jQuery Easing v1.3 - http://gsgd.co.uk/sandbox/jquery/easing/
 *
 * Uses the built in easing capabilities added In jQuery 1.1
 * to offer multiple easing options
 *
 * TERMS OF USE - jQuery Easing
 * 
 * Open source under the BSD License. 
 * 
 * Copyright © 2008 George McGinley Smith
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list of 
 * conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list 
 * of conditions and the following disclaimer in the documentation and/or other materials 
 * provided with the distribution.
 * 
 * Neither the name of the author nor the names of contributors may be used to endorse 
 * or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 *  COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 *  GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED 
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
 * OF THE POSSIBILITY OF SUCH DAMAGE. 
 *
*/

// t: current time, b: begInnIng value, c: change In value, d: duration
jQuery.easing['jswing'] = jQuery.easing['swing'];

jQuery.extend( jQuery.easing,
{
	def: 'easeOutQuad',
	swing: function (x, t, b, c, d) {
		//alert(jQuery.easing.default);
		return jQuery.easing[jQuery.easing.def](x, t, b, c, d);
	},
	easeInQuad: function (x, t, b, c, d) {
		return c*(t/=d)*t + b;
	},
	easeOutQuad: function (x, t, b, c, d) {
		return -c *(t/=d)*(t-2) + b;
	},
	easeInOutQuad: function (x, t, b, c, d) {
		if ((t/=d/2) < 1) return c/2*t*t + b;
		return -c/2 * ((--t)*(t-2) - 1) + b;
	},
	easeInCubic: function (x, t, b, c, d) {
		return c*(t/=d)*t*t + b;
	},
	easeOutCubic: function (x, t, b, c, d) {
		return c*((t=t/d-1)*t*t + 1) + b;
	},
	easeInOutCubic: function (x, t, b, c, d) {
		if ((t/=d/2) < 1) return c/2*t*t*t + b;
		return c/2*((t-=2)*t*t + 2) + b;
	},
	easeInQuart: function (x, t, b, c, d) {
		return c*(t/=d)*t*t*t + b;
	},
	easeOutQuart: function (x, t, b, c, d) {
		return -c * ((t=t/d-1)*t*t*t - 1) + b;
	},
	easeInOutQuart: function (x, t, b, c, d) {
		if ((t/=d/2) < 1) return c/2*t*t*t*t + b;
		return -c/2 * ((t-=2)*t*t*t - 2) + b;
	},
	easeInQuint: function (x, t, b, c, d) {
		return c*(t/=d)*t*t*t*t + b;
	},
	easeOutQuint: function (x, t, b, c, d) {
		return c*((t=t/d-1)*t*t*t*t + 1) + b;
	},
	easeInOutQuint: function (x, t, b, c, d) {
		if ((t/=d/2) < 1) return c/2*t*t*t*t*t + b;
		return c/2*((t-=2)*t*t*t*t + 2) + b;
	},
	easeInSine: function (x, t, b, c, d) {
		return -c * Math.cos(t/d * (Math.PI/2)) + c + b;
	},
	easeOutSine: function (x, t, b, c, d) {
		return c * Math.sin(t/d * (Math.PI/2)) + b;
	},
	easeInOutSine: function (x, t, b, c, d) {
		return -c/2 * (Math.cos(Math.PI*t/d) - 1) + b;
	},
	easeInExpo: function (x, t, b, c, d) {
		return (t==0) ? b : c * Math.pow(2, 10 * (t/d - 1)) + b;
	},
	easeOutExpo: function (x, t, b, c, d) {
		return (t==d) ? b+c : c * (-Math.pow(2, -10 * t/d) + 1) + b;
	},
	easeInOutExpo: function (x, t, b, c, d) {
		if (t==0) return b;
		if (t==d) return b+c;
		if ((t/=d/2) < 1) return c/2 * Math.pow(2, 10 * (t - 1)) + b;
		return c/2 * (-Math.pow(2, -10 * --t) + 2) + b;
	},
	easeInCirc: function (x, t, b, c, d) {
		return -c * (Math.sqrt(1 - (t/=d)*t) - 1) + b;
	},
	easeOutCirc: function (x, t, b, c, d) {
		return c * Math.sqrt(1 - (t=t/d-1)*t) + b;
	},
	easeInOutCirc: function (x, t, b, c, d) {
		if ((t/=d/2) < 1) return -c/2 * (Math.sqrt(1 - t*t) - 1) + b;
		return c/2 * (Math.sqrt(1 - (t-=2)*t) + 1) + b;
	},
	easeInElastic: function (x, t, b, c, d) {
		var s=1.70158;var p=0;var a=c;
		if (t==0) return b;  if ((t/=d)==1) return b+c;  if (!p) p=d*.3;
		if (a < Math.abs(c)) { a=c; var s=p/4; }
		else var s = p/(2*Math.PI) * Math.asin (c/a);
		return -(a*Math.pow(2,10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )) + b;
	},
	easeOutElastic: function (x, t, b, c, d) {
		var s=1.70158;var p=0;var a=c;
		if (t==0) return b;  if ((t/=d)==1) return b+c;  if (!p) p=d*.3;
		if (a < Math.abs(c)) { a=c; var s=p/4; }
		else var s = p/(2*Math.PI) * Math.asin (c/a);
		return a*Math.pow(2,-10*t) * Math.sin( (t*d-s)*(2*Math.PI)/p ) + c + b;
	},
	easeInOutElastic: function (x, t, b, c, d) {
		var s=1.70158;var p=0;var a=c;
		if (t==0) return b;  if ((t/=d/2)==2) return b+c;  if (!p) p=d*(.3*1.5);
		if (a < Math.abs(c)) { a=c; var s=p/4; }
		else var s = p/(2*Math.PI) * Math.asin (c/a);
		if (t < 1) return -.5*(a*Math.pow(2,10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )) + b;
		return a*Math.pow(2,-10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )*.5 + c + b;
	},
	easeInBack: function (x, t, b, c, d, s) {
		if (s == undefined) s = 0.6;
		return c*(t/=d)*t*((s+1)*t - s) + b;
	},
	easeOutBack: function (x, t, b, c, d, s) {
		if (s == undefined) s = 0.6;
		return c*((t=t/d-1)*t*((s+1)*t + s) + 1) + b;
	},
	easeInOutBack: function (x, t, b, c, d, s) {
		if (s == undefined) s = 1.70158; 
		if ((t/=d/2) < 1) return c/2*(t*t*(((s*=(1.525))+1)*t - s)) + b;
		return c/2*((t-=2)*t*(((s*=(1.525))+1)*t + s) + 2) + b;
	},
	easeInBounce: function (x, t, b, c, d) {
		return c - jQuery.easing.easeOutBounce (x, d-t, 0, c, d) + b;
	},
	easeOutBounce: function (x, t, b, c, d) {
		if ((t/=d) < (1/2.75)) {
			return c*(7.5625*t*t) + b;
		} else if (t < (2/2.75)) {
			return c*(7.5625*(t-=(1.5/2.75))*t + .75) + b;
		} else if (t < (2.5/2.75)) {
			return c*(7.5625*(t-=(2.25/2.75))*t + .9375) + b;
		} else {
			return c*(7.5625*(t-=(2.625/2.75))*t + .984375) + b;
		}
	},
	easeInOutBounce: function (x, t, b, c, d) {
		if (t < d/2) return jQuery.easing.easeInBounce (x, t*2, 0, c, d) * .5 + b;
		return jQuery.easing.easeOutBounce (x, t*2-d, 0, c, d) * .5 + c*.5 + b;
	}
});

/*
 *
 * TERMS OF USE - EASING EQUATIONS
 * 
 * Open source under the BSD License. 
 * 
 * Copyright © 2001 Robert Penner
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list of 
 * conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list 
 * of conditions and the following disclaimer in the documentation and/or other materials 
 * provided with the distribution.
 * 
 * Neither the name of the author nor the names of contributors may be used to endorse 
 * or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 *  COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 *  GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED 
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
 * OF THE POSSIBILITY OF SUCH DAMAGE. 
 *
 */
 
 
 
 /*
 The toolani transition plugin is property of Christian "Kriesi" Budschedl
 By buying this theme you are allowed to use it in commercial as well as in non commercial projects. 
 However you are not allowed to use it in free or premium themes and templates you intend to distribute. 
 
 Redistribution of the toolani transition plugin is not allowed, it will soon be available at toolani.com
 Please link directly to toolani.com if you want to spread the word ;)
 
 */
(function($)
{ 
	$.fn.toolani_transition = function(default_options) 
	{
		var defaults = 
		{  
			block_size: 50,
			delay:35, // show delay between each block
			duration:700, // transition duration for each block
			display_for:5000,
			transition_show:"easeInCubic", //easeInCubic, easeOutBounce
			transition_hide:"easeOutCubic" //easeOutCubic 
			
		};  
		var default_options = $.extend(defaults, default_options); 
		var block_count = 0;
		var obj = this;
		
		obj.each(function()
		{	
			if ($(this).find("img").length < 2){
			return false;
			}
			
			$(this).find("img:eq(0)").css({zIndex:1});
			$(this).find("img").css({position:"absolute"});
			$(this).css({overflow:"hidden"});
			var container_height = $(this).height(), container_width = $(this).width();
			var position_top = 0, position_left = 0;
			var mod_x = 0, mod_y = 0;
			var memorize = 0, offset_modifier = 1;
			var block_delay = default_options.delay;
			var generate = true;
			
		
			while(generate)
			{								
			/*here comes the math, this calculation creates and aligns the blocks diagonally*/
				if(mod_x >= (container_width/default_options.block_size))
				{
					mod_x = mod_x - offset_modifier; mod_y = mod_y + offset_modifier; offset_modifier ++;
				}
				position_top = (default_options.block_size * mod_y); position_left = (default_options.block_size * mod_x);
				
				if(mod_x == 0 || mod_y >= (container_height/default_options.block_size) -1)
				{
					memorize ++; mod_x = memorize; mod_y = 0;
				}else{
					mod_x --; mod_y ++;
				}
				if(position_top >= container_height){generate = false;}
				
			/*alignment of blocks end */
				
				if(generate)
				{	
					block_count++;
					
					var	block = $("<div></div>").appendTo($(this)).css(
						{
						opacity:0,
						position:"absolute",
						height: 1,
						width: 1,
						zIndex:3,
						top:position_top+default_options.block_size/2,
						left:position_left+default_options.block_size/2
						}).data("Data",{delay:block_count,top:position_top,left:position_left});
				}
			}
	  });
		
		var blocks = $("#transition-container div");
		var image_count = 1;
		var background_image;
		var images = obj.find("img");
		
		function transition()
		{
			background_image = obj.find("img:eq("+ image_count +")").attr("src");	
			blocks.css({backgroundImage:"url('"+background_image+"')"}).each(function(i)
			{
				var data = $(this).data("Data");
				var offset = "-"+data.left +"px -"+data.top+"px";
				
				$(this).css({backgroundPosition:offset}).animate({timer:1}, 1000 + default_options.delay * data.delay).animate({
																															   
				 top:data.top,
				 left:data.left,
				 height:default_options.block_size, 
				 width:default_options.block_size, 
				 opacity:1},default_options.duration,default_options.transition_show,function(){
					 if(i+1 == block_count){swapimage();}
					 });			
			});
		};
		
		function swapimage(){
			
			images.each(function(i)
			{
				i == image_count ? $(this).css({zIndex:2}) : $(this).css({zIndex:1});				
			});
			
			if(image_count+1  >= images.length)
				{
				image_count = 0;
				}else{
				image_count ++;	
				}
			
			blocks.each(function(){
			var position_top = 	parseInt($(this).css('top').replace(/px/, "")) + default_options.block_size/2;
			var position_left = parseInt($(this).css('left').replace(/px/, ""))+ default_options.block_size/2;			
			$(this).animate({timer:1}, (default_options.duration*2)).animate({opacity:0,height:"1px",width:"1px",top:position_top,left:position_left},0);});
			}
		setInterval(transition, default_options.display_for);
		
		
		
	};
})(jQuery); 