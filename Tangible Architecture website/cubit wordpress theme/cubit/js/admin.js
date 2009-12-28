jQuery.noConflict();
function change_admin(){
	var specialfields = jQuery('.jquery_move');
	var wrapper = jQuery("#normal-sortables");
	
	specialfields.each(function(){								
		wrapper.prepend(jQuery(this).html());
		jQuery(this).html("");
								});
	
	}
	

jQuery(document).ready(function(){
	change_admin();
});
