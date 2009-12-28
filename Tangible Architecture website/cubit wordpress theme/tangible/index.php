<?php global $kriesi_options; get_header(); ?>

<?php if($kriesi_options['fill_main'] == 1 || $kriesi_options['fill_main'] == ""){ 


//*if you did choose: "edit by html" you can now edit the following lines: *//
?>

	<div class="info_box box1 entry">
        <div class="box_image"><!-- insert image here--></div>
        <span class="meta">adipisicing elit, sed do</span>
        <h2>Lorem Ipsum dolor</h2>
        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. </p>
        <p>Ut enim ad minim veniam.<br/><br/> quis nostrud exercitation ullamco laboris nisi ut aliquip ex</p>
    </div>
    
    <div class="info_box box2">
        <div class="box_image"><!-- insert image here--></div>
        <span class="meta">adipisicing elit, sed do</span>
        <h2>Lorem Ipsum dolor</h2>
        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. </p>
        <p><br/><br/>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex</p>
    </div>
    
    <div class="info_box box3">
        <div class="box_image"><!-- insert image here--></div>
        <span class="meta">adipisicing elit, sed do</span>
        <h2>Lorem Ipsum dolor</h2>
        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.<br/><br/> sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. </p>
        <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex</p>
    </div>
    
<?php 
//stop editing here if you did choose "edit by html"


	}else if($kriesi_options['fill_main'] == 2){ 
	$query_string .= "&showposts=3";
	$query_string .= "&cat=".$kriesi_options['com_cat'];
	query_posts($query_string);
	$counter = 1;
	
	if (have_posts()) : while (have_posts()) : the_post(); 
	
	$preview_pic_mainpage = get_post_meta($post->ID, "preview_pic_mainpage", true);
	$punchline = get_post_meta($post->ID, "punchline", true);
	?>
    
         
         		<div class="info_box box<?php echo $counter; $counter++; ?>">
                    <div class="box_image"><img src='<?php echo $preview_pic_mainpage; $preview_pic_mainpage =''; ?>' alt="" /></div>
                    <span class="meta"><?php echo $punchline; $punchline='';?></span>
                    <h2><?php the_title(); ?></h2>
                    <?php the_content('Read more'); ?>
                    <?php edit_post_link('Edit','', '' ); ?>
                </div>
		<?php endwhile; endif; ?>
    
    
    <?php } ?>
<?php get_footer(); ?>
