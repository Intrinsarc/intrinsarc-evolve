<div class="sidebar">
<?php 
if($post->post_parent)
  $children = wp_list_pages("title_li=&sort_column=menu_order&child_of=".$post->post_parent."&echo=0");
  else
  $children = wp_list_pages("title_li=&sort_column=menu_order&child_of=".$post->ID."&echo=0");
  if ($children) { ?>
  <ul id="subnav">
  <?php echo $children; ?>
  </ul>
  <?php } ?>
  
  
  <?php if (function_exists('dynamic_sidebar') && is_page() && !is_front_page() && dynamic_sidebar('Pages Sidebar') ) : endif; ?> 
  <?php if (function_exists('dynamic_sidebar') && !is_page() && dynamic_sidebar('News Section Sidebar') ) : endif; ?> 
  

</div><!-- end sidebar -->
