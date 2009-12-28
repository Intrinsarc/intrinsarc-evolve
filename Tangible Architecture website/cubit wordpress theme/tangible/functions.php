<?php
/*
Attention: all functions within this file are developed by Christian "Kriesi" Budschedl
You are allowed to use them in non-commercial projects as well as in commercial projects. What you are not allowed to do is to redistribute the functions or part of them (eg in wordpress templates)

Contact: office@kriesi.at or at http://www.kriesi.at/contact
*/

global $kriesi_options;
$kriesi_options  = get_option('kriesi_options');
if(!is_admin()){
add_action('wp_print_scripts', 'addjquery');
}

function addjquery() {
	$my_jquery = get_bloginfo('template_url'). "/js/jquery-1.3.1.min.js";
	wp_deregister_script( 'jquery' ); 
    wp_register_script( 'jquery', $my_jquery, false, '' ); 
	wp_enqueue_script('jquery');
	
	$my_customJs = get_bloginfo('template_url')."/js/custom.js";
	wp_enqueue_script('my_customJs',$my_customJs,array('jquery'));
}




class simple_breadcrumb{
	var $options;
	
function simple_breadcrumb(){
	
	$this->options = array( 	//change this array if you want another output scheme
	'before' => '<span> ',
	'after' => ' </span>',
	'delimiter' => '&raquo;'
	);
	
	$markup = $this->options['before'].$this->options['delimiter'].$this->options['after'];
	
	global $post;
	echo '<p class="breadcrumb"><span class="breadcrumb_info">You are here:</span> <a href="'.get_option('home').'">';
		bloginfo('name');
		echo "</a>";
		if(!is_front_page()){echo $markup;}
				
		$output = $this->simple_breadcrumb_case($post);
		
		echo "<span class='current_crumb'>";
		if ( is_page() || is_single()) {
		the_title();
		}else{
		echo $output;
		}
		echo " </span></p>";
	}
	
function simple_breadcrumb_case($der_post){
	$markup = $this->options['before'].$this->options['delimiter'].$this->options['after'];
	if (is_page()){
		 if($der_post->post_parent) {
			 $my_query = get_post($der_post->post_parent);			 
			 $this->simple_breadcrumb_case($my_query);
			 $link = '<a href="';
			 $link .= get_permalink($my_query->ID);
			 $link .= '">';
			 $link .= ''. get_the_title($my_query->ID) . '</a>'. $markup;
			 echo $link;
		  }	
	return;			 	
	} 
			
			
	if(is_single()){
		$category = get_the_category();
		if (is_attachment()){
		
			$my_query = get_post($der_post->post_parent);			 
			$category = get_the_category($my_query->ID);
			$ID = $category[0]->cat_ID;

			echo get_category_parents($ID, TRUE, $markup, FALSE );
			previous_post_link("%link $markup");
			
		}else{
			$ID = $category[0]->cat_ID;
			echo get_category_parents($ID, TRUE, $markup, FALSE );

		}
	return;
	}
	
	if(is_category()){
		$category = get_the_category(); 
		$i = $category[0]->cat_ID;
		$parent = $category[0]-> category_parent;
		
		if($parent > 0 && $category[0]->cat_name == single_cat_title("", false)){
			echo get_category_parents($parent, TRUE, $markup, FALSE);
		}
	return single_cat_title('',FALSE);
	}
	
	
	if(is_author()){
		$curauth = get_userdatabylogin(get_query_var('author_name'));
		return "Author: ".$curauth->nickname;
	}
	if(is_tag()){ return "Tag: ".single_tag_title('',FALSE); }
	
	if(is_404()){ return "404 - Page not Found"; }
	
	if(is_search()){ return "Search"; }	
	
	if(is_year()){ return get_the_time('Y'); }
	
	if(is_month()){
	$k_year = get_the_time('Y');
	echo "<a href='".get_year_link($k_year)."'>".$k_year."</a>".$markup;
	return get_the_time('F'); }
	
	if(is_day() || is_time()){ 
	$k_year = get_the_time('Y');
	$k_month = get_the_time('m');
	$k_month_display = get_the_time('F');
	echo "<a href='".get_year_link($k_year)."'>".$k_year."</a>".$markup;
	echo "<a href='".get_month_link($k_year, $k_month)."'>".$k_month_display."</a>".$markup;

	return get_the_time('jS (l)'); }
	
	}

}

function kriesi_pagination($query_string){
global $posts_per_page, $paged;
$my_query = new WP_Query($query_string ."&posts_per_page=-1");
$total_posts = $my_query->post_count;
if(empty($paged))$paged = 1;
$prev = $paged - 1;							
$next = $paged + 1;	
$range = 2; // only edit this if you want to show more page-links
$showitems = ($range * 2)+1;

$pages = ceil($total_posts/$posts_per_page);
if(1 != $pages){
	echo "<div class='pagination'>";
	echo ($paged > 2 && $paged > $range+1 && $showitems < $pages)? "<a href='".get_pagenum_link(1)."'>&laquo;</a>":"";
	echo ($paged > 1 && $showitems < $pages)? "<a href='".get_pagenum_link($prev)."'>&lsaquo;</a>":"";
	
		
	for ($i=1; $i <= $pages; $i++){
	if (1 != $pages &&( !($i >= $paged+$range+1 || $i <= $paged-$range-1) || $pages <= $showitems )){
	echo ($paged == $i)? "<span class='current'>".$i."</span>":"<a href='".get_pagenum_link($i)."' class='inactive' >".$i."</a>"; 
	}
	}
	
	echo ($paged < $pages && $showitems < $pages) ? "<a href='".get_pagenum_link($next)."'>&rsaquo;</a>" :"";
	echo ($paged < $pages-1 &&  $paged+$range-1 < $pages && $showitems < $pages) ? "<a href='".get_pagenum_link($pages)."'>&raquo;</a>":"";
	echo "</div>\n";
	}
}

################ admin

function kriesi_admin_panel() {	
	if (!current_user_can('level_7')){
		return;
	}else{
	include('kriesioptions.php');
	add_theme_page ('Cubit Options', 'Cubit Options', 7, 'kriesioptions.php', 'k_generate');
	}
}
add_action('admin_menu', 'kriesi_admin_panel');





global $fields, $fieldsname;

$fields[0]= "preview_pic_mainpage";
$fields[1]= "punchline";
$fields[2]= "portfolio_big";

$fieldsname[0]= "Small Mainpage Preview Image (280px x 60px)";
$fieldsname[1]= "Mainpage Punchline (above post tilte)";
$fieldsname[2]= "Portfolio Image Big Pic (562px x 160px)";

function kriesi_create_form(){
global $fields, $fieldsname;
$loop = count($fields);

if(isset($_GET['post'])){
$post_id = $_GET['post'];
}

?>

<div class="jquery_move">
<div class="postbox koption" id="projektsdiv">
<div title="Click to toggle" class="handlediv"></div>
<h3 class"hndle">Cubit Mainpage options</h3>
<div class="inside">
<p class="centerme"><strong>These options ONLY work for the blogs mainpage</strong></p>
<?php 

for ($i=0; $i<2; $i++){
	if ($post_id != ""){
		$current_field = $fields[$i];
		$value = get_post_meta($post_id, $current_field, true);
	}
	
	echo "<p class='extra_$i'><label for='".$fields[$i]."'>".$fieldsname[$i].": </label><input id='".$fields[$i]."' name='".$fields[$i]."' type='text' value='$value'></p>";

	
} ?>
</div></div></div>
<div class="jquery_move">
<div class="postbox koption" id="projektsdiv2">
<div title="Click to toggle" class="handlediv"></div>
<h3 class"hndle">Cubit Portfolio options</h3>
<div class="inside">
<p class="centerme"><strong>These options ONLY work for your news &amp; portfolio section</strong><br/>enter the url of the image you want to display here, for example: http://yourdomain/images/yourimage.jpg</p>
<?php 

for ($i=2; $i<$loop; $i++){
	if ($post_id != ""){
		$current_field = $fields[$i];
		$value = get_post_meta($post_id, $current_field, true);
	}
	
	echo "<p class='extra_$i'><label for='".$fields[$i]."'>".$fieldsname[$i].": </label><input id='".$fields[$i]."' name='".$fields[$i]."' type='text' value='$value'></p>";

	
} ?>
</div></div></div>



<?php }
	
function kriesi_save_data($post_id){
global $fields;
$loop = count($fields);

	for ($i=0; $i<$loop; $i++){
		$current_field = $fields[$i];
		kriesi_insert_data($current_field, $post_id);		
		}
}

function kriesi_insert_data($current_field, $post_id){
global $fields;
$new_value = $_POST[$current_field];
		$value = get_post_meta($post_id, $current_field, true);
		
		if ($new_value == ""){
					if ($value != ""){
						delete_post_meta($post_id, $current_field, $value);
					}
		
				} else{
					if ($value == ""){
						add_post_meta($post_id, $current_field, $new_value, true);
					}else if ($value != $new_value ){
						update_post_meta($post_id, $current_field, $new_value, $value);	
					}
				}
			}


function kriesi_append_admin_head(){?>
<link rel='stylesheet' href='<?php echo bloginfo('template_url'); ?>/admin.css' type='text/css' />
<script type="text/javascript" src="<?php echo bloginfo('template_url'); ?>/js/admin.js"></script>
<?php }

add_action('admin_head','kriesi_append_admin_head');
add_action('edit_form_advanced','kriesi_create_form',1);
add_action('save_post','kriesi_save_data');





function kriesi_no_generator() { return ''; }  
add_filter('the_generator','kriesi_no_generator');

// Widget Settings

if ( function_exists('register_sidebar') )
	register_sidebar(array(
		'name' => 'Pages Sidebar',
		'before_widget' => '<div id="%1$s" class="widget %2$s">', 
	'after_widget' => '</div>', 
	'before_title' => '<h3 class="widgettitle">', 
	'after_title' => '</h3>', 
	));

if ( function_exists('register_sidebar') )
	register_sidebar(array(
		'name' => 'News Section Sidebar',
		'before_widget' => '<div id="%1$s" class="widget %2$s">', 
	'after_widget' => '</div>', 
	'before_title' => '<h3 class="widgettitle">', 
	'after_title' => '</h3>', 
	));


?>