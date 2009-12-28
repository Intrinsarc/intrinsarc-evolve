<?php 
	global $kriesi_options;
	if($kriesi_options['com_cat'] != "" && in_category($kriesi_options['com_cat'])){
	header("Location:".get_settings('home'));
	}
	
	get_header(); 
	get_sidebar();?>

<div class="content">
    <?php if (have_posts()) : while (have_posts()) : the_post();?>
		<div class="entry">
		<span class="meta meta_subpage">
        	<span class="meta_date"><?php the_time('d.m.Y') ?></span>
            <span class="meta_cat"><?php the_category(', '); ?></span>
            <span class="meta_comment"><?php comments_number('No Comments', '1 Comment', '% Comments'); ?></span>
            <?php edit_post_link('Edit', '<span class="meta_comment">', '</span>'); ?>
        </span>
        
		<h2 id="post-<?php the_ID(); ?>"><?php the_title(); ?></h2>
        <?php 
				if(get_post_meta($post->ID, "portfolio_big", true))
				{ 
					$bigpic = get_post_meta($post->ID, "portfolio_big", true);
				?>
                <div class="preview_pic">
 <a href='<?php the_permalink(); ?>' title='<?php the_title(); ?>' ><img src ="<?php echo $bigpic; ?>" alt="<?php the_title(); ?>" /></a>
				</div>
				<?php
				}
				?>
			
				<?php the_content('read more'); ?>

				<?php link_pages('<p><strong>Pages:</strong> ', '</p>', 'number'); ?>
				<!--<?php edit_post_link('Edit this entry.', '', ''); ?>-->
        </div>
        <div class="entry">
        <?php comments_template(); ?>
        </div>
        
	  <?php endwhile; 
			if (function_exists(kriesi_pagination)){
				kriesi_pagination($query_string);
			}
	  endif; ?>

         <!--edit link-->
	

</div><!-- end content-->
<?php get_footer(); ?>