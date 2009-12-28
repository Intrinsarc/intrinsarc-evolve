<?php
/*
Template Name: Contact Form
*/
?>
<?php 

function checkmymail($mailadresse){
	$email_flag=preg_match("!^\w[\w|\.|\-]+@\w[\w|\.|\-]+\.[a-zA-Z]{2,4}$!",$mailadresse);
	return $email_flag;
}

if(isset($_POST['Send'])){
	$error = false;
	if($_POST['yourname'] != ""){$class1 = "class='ajax_valid'";}else{$class1 = "class='ajax_false'"; $error = true;}
	if(checkmymail($_POST['email'])){$class2 = "class='ajax_valid'";}else{$class2 = "class='ajax_false'"; $error = true;}
	if($_POST['message'] != ""){$class3 = "class='ajax_valid'";}else{$class3 = "class='ajax_false'"; $error = true;}
		$the_name = $_POST['yourname'];
		$email = $_POST['email'];
		$message = $_POST['message'];
		$website = $_POST['website'];
	if($error == false){
	
		
	
 		$to      =  $_POST['myemail'];
		$subject = "New Message from " . $_POST['myblogname'];
		$header  = 'MIME-Version: 1.0' . "\r\n";
		$header .= 'Content-type: text/html; charset=utf-8' . "\r\n";
		$header .= 'From:'. $email . " \r\n";
	
		$message1 = nl2br($_POST['message']);
		$message = "New message from  $the_name <br/>
		Mail: $email<br />
		Website: $website <br /><br />
		Message: $message1 <br />";
		
		mail($to,
		$subject,
		$message,
		$header); 
		
		$noform = true;
		}
	
	}
	
	get_header(); ?>
<?php get_sidebar(); ?>

<div class="content">
    <?php if (have_posts()) : while (have_posts()) : the_post(); ?>
		
                 <!--post title-->
		<h2 id="post-<?php the_ID(); ?>"><?php the_title(); ?></h2>
			
                              <!--post with more link -->
				<?php the_content('<p class="serif">Read the rest of this page &raquo;</p>'); ?>

	                       <!--if you paginate pages-->
				<?php link_pages('<p><strong>Pages:</strong> ', '</p>', 'number'); ?>
	
	<!--end of post and end of loop-->
	  <?php endwhile; endif; ?>

         <!--edit link-->
	<?php edit_post_link('Edit this entry.', '<p>', '</p>'); ?>
    

<div id="ajax_response"></div>
        <div id="ajax_form">
        <form action="<?php echo get_permalink(); ?>" method="post" class="ajax_form">
<fieldset><?php if ($noform != true){ ?><legend><span>Send us a mail</span></legend>

<p <?php echo $class1?> ><input name="yourname" class="text_input empty" type="text" id="name" size="20" value='<?php echo $the_name?>'/><label for="name">Your Name*</label>
</p>
<p <?php echo $class2?> ><input name="email" class="text_input email" type="text" id="email" size="20" value='<?php echo $email?>' /><label for="email">E-Mail*</label></p>
<p><input name="website" class="text_input" type="text" id="website" size="20" value="<?php echo $website?>"/><label for="website">Website</label></p>
<label for="message" class="blocklabel">Your Message*</label>
<p <?php echo $class3 ?>><textarea name="message" class="text_area empty" cols="40" rows="7" id="message" ><?php echo $message ?></textarea></p>


<p>
<input type="hidden" id="myemail" name="myemail" value="<?php echo get_option('admin_email'); ?>" />
<input type="hidden" id="myblogname" name="myblogname" value="<?php echo get_option('blogname'); ?>" />

<input name="Send" type="submit" value="Send" class="button" id="send" size="16"/></p>
<?php } else { ?> 
<p><h3>Your message has been sent!</h3> Thank you!</p>

<?php } ?>
</fieldset>

</form> 
</div>
	

</div><!-- end content-->
<?php get_footer(); ?>