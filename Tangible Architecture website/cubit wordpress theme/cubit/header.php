<?php global $kriesi_options; ?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head profile="http://gmpg.org/xfn/11"> 
<meta http-equiv="Content-Type" content="<?php bloginfo('html_type'); ?>; charset=<?php bloginfo('charset'); ?>" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta name="Cube_option1" content="<?php echo bloginfo('template_url'); ?>" />
<title><?php if (is_home()) { bloginfo('name'); ?><?php } elseif (is_category() || is_page() ||is_single()) { ?> <?php } ?><?php wp_title(''); ?></title>

<link rel="alternate" type="application/rss+xml" title="<?php bloginfo('name'); ?> RSS Feed" href="<?php bloginfo('rss2_url'); ?>" />
<link rel="pingback" href="<?php bloginfo('pingback_url'); ?>" />

<link rel="stylesheet" href="<?php bloginfo('stylesheet_url'); ?>" type="text/css" media="screen" />
<?php
if ( is_singular() ) wp_enqueue_script( 'comment-reply' );
?>
<?php if($kriesi_options['whichdesign'] == 6){ ?>
<link rel="stylesheet" href="<?php echo bloginfo('template_url'); ?>/css/style6.css" type="text/css" media="screen" />
<?php } else if($kriesi_options['whichdesign'] == 5){ ?>
<link rel="stylesheet" href="<?php echo bloginfo('template_url'); ?>/css/style5.css" type="text/css" media="screen" />
<?php } else if($kriesi_options['whichdesign'] == 4){ ?>
<link rel="stylesheet" href="<?php echo bloginfo('template_url'); ?>/css/style4.css" type="text/css" media="screen" />
<?php } else if($kriesi_options['whichdesign'] == 3){ ?>
<link rel="stylesheet" href="<?php echo bloginfo('template_url'); ?>/css/style3.css" type="text/css" media="screen" />
<?php } else if($kriesi_options['whichdesign'] == 2){ ?>
<link rel="stylesheet" href="<?php echo bloginfo('template_url'); ?>/css/style2.css" type="text/css" media="screen" />
<?php } else {?> 
<link rel="stylesheet" href="<?php echo bloginfo('template_url'); ?>/css/style1.css" type="text/css" media="screen" />
<?php }

wp_head();

//dont remove the following lines, they help prevent google duplicate content problem
if ( ( is_single() || is_page() || is_home() ) && ( !is_paged() ) ) {
        echo '<meta name="robots" content="index, follow" />' . "\n";
} else {
        echo '<meta name="robots" content="noindex, follow" />' . "\n";
}

// if you want to display the image slider on each page you have to either remove the if statement and always define the $frontpage variable or add aditional sites with wordpress conditional tags: (google it if you dont know what that means, its really simple ;) )

if(is_front_page()){
	$frontpage = " id='frontpage' ";
	}
?>


</head>
<body <?php if($frontpage){echo $frontpage;} ?>>
<div id="top">
	<div id="head">
		<h1 id='logo'><a href="<?php echo get_settings('home'); ?>/"><?php bloginfo('name'); ?></a></h1>
            <ul id="nav">
                <li <?php if(is_front_page()){ echo "class='current_page_item' ";}?>><a href="<?php echo get_settings('home'); ?>">Home</a></li>
                
                <?php 
				
				if(preg_match("/exclude/", $kriesi_options['com_page']) && $kriesi_options['slide_page'] != "")
				{
					$exclude = explode("exclude=", strtolower($kriesi_options['com_page']));	
					$exclude[1] =  "exclude=".$kriesi_options['slide_page'].",".$exclude[1]; 
					$main_menu_query = implode("",$exclude);
				}
				else if($kriesi_options['slide_page'] != "")
				{
					$main_menu_query = "exclude=".$kriesi_options['slide_page']."&".$kriesi_options['com_page']."&";
				}else{
					$main_menu_query = $kriesi_options['com_page'];
				}
				
				
				wp_list_pages('title_li=&sort_column=menu_order&'.$main_menu_query); ?>
            </ul>
    	<?php if($frontpage){?>
		<div id="main_transition">
        	<div id="transition-container">
            <?php if($kriesi_options['slide_page'] != ""){
			$my_query = new WP_Query('page_id='.$kriesi_options['slide_page']);
			
			if ($my_query->have_posts()) :  while ($my_query->have_posts()) : $my_query->the_post();
           
		   	the_content();
	 		
			endwhile;  endif;

			}else{ ?>
            <!-- WANT TO EDIT YOUR SLIDESHOW IMAGES DIRECTLY? ADD ANY NUMBER OF IMAGES YOU LIKE BELOW-->
            
            <img src='<?php echo bloginfo('template_url'); ?>/images<?php echo $kriesi_options['whichdesign']; ?>/header_image1.jpg' alt='' height="240" width="900" />
            <img src='<?php echo bloginfo('template_url'); ?>/images<?php echo $kriesi_options['whichdesign']; ?>/header_image2.jpg' alt='' height="240" width="900" />
            
            <?php } ?>
        	</div>
		</div><!-- end main_transition-->
		<?php } else{
    	 if (class_exists('simple_breadcrumb')) { $bc = new simple_breadcrumb; }
    	 } ?>
	</div><!-- end head-->
<div id="main">
    