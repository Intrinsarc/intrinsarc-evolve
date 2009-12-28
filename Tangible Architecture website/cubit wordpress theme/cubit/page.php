<?php get_header(); ?>
<?php get_sidebar(); ?>

<div class="content">
		<div class="entry">

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
	
	</div>
</div><!-- end content-->
<?php get_footer(); ?>