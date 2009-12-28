<?php get_header(); ?>
<?php get_sidebar(); ?>
	
<div class="content">
	<?php if (have_posts()) : ?>
    <h3>Search Results</h3>
	
    <?php while (have_posts()) : the_post();?>
		<div class="entry">
		<span class="meta meta_subpage">
        	<span class="meta_date"><?php the_time('d.m.Y') ?></span>
            <span class="meta_cat"><?php the_category(', '); ?></span>
            <span class="meta_comment"><?php comments_popup_link('No Comments', '1 Comment', '% Comments'); ?></span>
            <?php edit_post_link('Edit', '<span class="meta_comment">', '</span>'); ?>
        </span>
        
	<h2 class="post_title" id="post-<?php the_ID(); ?>"><a href="<?php the_permalink(); ?>" title="<?php the_title(); ?>" ><?php the_title(); ?></a></h2>			
				<?php the_content('read more'); ?>

				<?php link_pages('<p><strong>Pages:</strong> ', '</p>', 'number'); ?>
				
        </div>
	  <?php endwhile; 
			if (function_exists(kriesi_pagination)){
				kriesi_pagination($query_string);
			}
	  endif; ?>

         <!--edit link-->
	

</div><!-- end content-->
<?php get_footer(); ?>