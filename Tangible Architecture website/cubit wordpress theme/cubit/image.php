<?php
get_header();
?>



  <?php if (have_posts()) : while (have_posts()) : the_post(); ?>

		<!--thte title of the attachment-->
			<a href="<?php echo get_permalink($post->post_parent); ?>" rev="attachment"><?php echo get_the_title($post->post_parent); ?></a> &raquo; <?php the_title(); ?>
		<!--the attachment url default with the sice of medium-->
				<p class="attachment"><a href="<?php echo wp_get_attachment_url($post->ID); ?>"><?php echo wp_get_attachment_image( $post->ID, 'medium' ); ?></a></p>
				<div class="caption"><?php if ( !empty($post->post_excerpt) ) the_excerpt(); // this is the "caption"  and you need the class caption?></div>

				<?php the_content('<p class="serif">Read the rest of this entry &raquo;</p>'); ?>

				<!--navigation for all attachments of one article /post-->
					<?php previous_image_link() ?>
					<?php next_image_link() ?>
			
				<br class="clear" /><!--you need this class clear means:clear:both in style.css-because there are so many floats -->

                            <!--all options if pingbacks on or not, if comments are open or not and so on-->
				<p> <?php the_time('l, F jS, Y') ?> at <?php the_time() ?>and is filed under <?php the_category(', ') ?>.
						You can follow any responses to this entry through the <?php comments_rss_link('RSS 2.0'); ?> feed. 
						
						<?php if (('open' == $post-> comment_status) && ('open' == $post->ping_status)) {
							// Both Comments and Pings are open ?>
							You can <a href="#respond">leave a response</a>, or <a href="<?php trackback_url(true); ?>" rel="trackback">trackback</a> from your own site.
						
						<?php } elseif (!('open' == $post-> comment_status) && ('open' == $post->ping_status)) {
							// Only Pings are Open ?>
							Responses are currently closed, but you can <a href="<?php trackback_url(true); ?> " rel="trackback">trackback</a> from your own site.
						
						<?php } elseif (('open' == $post-> comment_status) && !('open' == $post->ping_status)) {
							// Comments are open, Pings are not ?>
							You can skip to the end and leave a response. Pinging is currently not allowed.
			
						<?php } elseif (!('open' == $post-> comment_status) && !('open' == $post->ping_status)) {
							// Neither Comments, nor Pings are open ?>
							Both comments and pings are currently closed.			
						
						<?php } edit_post_link('Edit this entry.','',''); ?>
						
					
				</p><!--all options over and out-->

<!--the comments-->

	<?php comments_template(); ?>

	<?php endwhile; else: ?><!--end of one post and-->

		<p>Sorry, no attachments matched your criteria.</p>

<?php endif; ?><!--end of the loop-->

<!--get the footer.php-->

<?php get_footer(); ?>
