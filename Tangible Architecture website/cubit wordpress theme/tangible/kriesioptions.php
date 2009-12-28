<?php
global $kriesi_options;

function k_generate(){
$options = $newoptions  = get_option('kriesi_options');

if ( $_POST['kriesi_options'] ) {
		$newoptions['slide_page'] = strip_tags(stripslashes($_POST['slide_page']));
		$newoptions['whichdesign'] = strip_tags(stripslashes($_POST['whichdesign']));
		$newoptions['fill_main'] = strip_tags(stripslashes($_POST['fill_main']));
		$newoptions['com_cat'] = strip_tags(stripslashes($_POST['com_cat']));
		$newoptions['com_port'] = strip_tags(stripslashes($_POST['com_port']));
		$newoptions['com_page'] = strip_tags(stripslashes($_POST['com_page']));
		$newoptions['enable_exc_post'] = strip_tags(stripslashes($_POST['enable_exc_post']));
		$newoptions['google_analaytics'] = stripslashes($_POST['google_analaytics']);
		}
		
	if ( $options != $newoptions ) {
		$options = $newoptions;
		update_option('kriesi_options', $options);
		}
		
$slide_page = empty( $options['slide_page'] ) ? "" : $options['slide_page'];
$whichdesign = empty( $options['whichdesign'] ) ? 1 : $options['whichdesign'];
$fill_main = empty( $options['fill_main'] ) ? 1 : $options['fill_main'];
$com_cat = empty( $options['com_cat'] ) ? "" : $options['com_cat'];
$com_port = empty( $options['com_port'] ) ? "" : $options['com_port'];
$com_page = empty( $options['com_page'] ) ? "" : $options['com_page'];
$enable_exc_post = empty( $options['enable_exc_post'] ) ? false : true;
$google_analaytics = empty( $options['google_analaytics'] ) ? "" : $options['google_analaytics'];

?>
<div class="wrap">
<h2>Cubit Options</h2>

<form method="post" action="">
<table class="form-table">
<tr valign="top">
  <th scope="row">Cubit Design</th>
  <td><br/>
    <label><input type="radio" name="whichdesign" id="whichdesign" value="1" <?php if ($whichdesign == 1){echo "checked = checked";}?> /> Design 1 - Cubit Light/Blue</label><br/>
    <label><input type="radio" name="whichdesign" id="whichdesign2" value="2" <?php if ($whichdesign == 2){echo "checked = checked";}?>/> Design 2 - Cubit Light/Green</label><br/>
    <label><input type="radio" name="whichdesign" id="whichdesign3" value="3" <?php if ($whichdesign == 3){echo "checked = checked";}?>/> Design 3 - Cubit Light/Orange</label><br/>
    <label><input type="radio" name="whichdesign" id="whichdesign4" value="4" <?php if ($whichdesign == 4){echo "checked = checked";}?>/> Design 4 - Cubit Dark/Pink</label><br/>
	<label><input type="radio" name="whichdesign" id="whichdesign5" value="5" <?php if ($whichdesign == 5){echo "checked = checked";}?>/> Design 5 - Cubit Dark/Gold</label><br/>
	<label><input type="radio" name="whichdesign" id="whichdesign6" value="6" <?php if ($whichdesign == 6){echo "checked = checked";}?>/> Design 6 - Cubit Dark/Crimson</label><br/>
    </td>
</tr>
<tr valign="top">
  <th scope="row">Cubit Starting Page Image Slider</th>
  <td><br/>
  Here you can choose which way you want to populate the Image slider on your starting page. <br/>
  You can select an existing page and add all images to this page. If no page is selected the script uses the images defined in your header.php file. If you want to, you can edit the images directly in this file. <br/>

<select class="postform" id="slide_page" name="slide_page"> 
<option value="">Select Page</option>  
<?php 
	$pages = get_pages('title_li=&orderby=name');
	foreach ($pages as $page){
		
		if ($slide_page == $page->ID){
		$selected = "selected='selected'";	
			}else{
		$selected = "";		
		}
		echo"<option $selected value='". $page->ID."'>". $page->post_title."</option>";
		}
?>
</select>
    </td>
</tr>



<tr valign="top">
  <th scope="row">Cubit Starting Page Content</th>
  <td><br/>
  Here you can choose in which way you want to add content to your starting page. <br/>
  You can either choose to edit the index.php file directly, or fill the page with 3 posts. <br/>
  If you choose <strong>posts</strong> please select a category as well. This category will be excluded from the news section.<br/>

  	   <br/>
    <label><input type="radio" name="fill_main" id="fill_main" value="1" <?php if ($fill_main == 1){echo "checked = checked";}?> /> Use HTML (edit html in index.php)</label><br/>
    <label><input type="radio" name="fill_main" id="fill_main" value="2" <?php if ($fill_main == 2){echo "checked = checked";}?> /> Use Posts</label><br/>
    
    Category for Posts IF using post is selected:<br/>

<select class="postform" id="com_cat" name="com_cat"> 
<option value="">Select Category</option>  
<?php 
	$categories = get_categories('title_li=&orderby=name');
	foreach ($categories as $category){
		
		if ($com_cat == $category->term_id){
		$selected = "selected='selected'";	
			}else{
		$selected = "";		
		}
		echo"<option $selected value='". $category->term_id."'>". $category->name."</option>";
		}
?>
</select>
    </td>
</tr>


<tr valign="top">
  <th scope="row">Cubit Portfolio Page Content</th>
  <td><br/>
  Here you can choose which category is your portfolio category.<br/> This category will be excluded from the news section.<br/>

  	   <br/>
<select class="postform" id="com_port" name="com_port"> 
<option value="">Select Category</option>  
<?php 
	$categories = get_categories('title_li=&orderby=name');
	foreach ($categories as $category){
		
		if ($com_port == $category->term_id){
		$selected = "selected='selected'";	
			}else{
		$selected = "";		
		}
		echo"<option $selected value='". $category->term_id."'>". $category->name."</option>";
		}
?>
</select>
    </td>
</tr>

<tr valign="top">
<th scope="row"><label for="com_page">Page Navigation</label></th>
<td>

<input name="com_page" type="text" id="com_page" value="<?php if ($com_page){echo $com_page;}?>" size="70" maxlength="255" /><br/>
	Enter the query string of the pages you want to display in the main menu (top right corner of the page)<br/>
    If left empty it will display all pages<br/>
    Some Examples:<br/>
    <strong>include=9,16,22,24,33</strong> (this would display 5 menu items for the pages with the id 9, 16, 22, 24, 33)<br/>
    <strong>exclude=2,6,12</strong> (this would display menu itemsfor all pages except those with id 2, 6, 12)<br/>
</td>
</tr>

<tr valign="top">
<th scope="row"><label for="google_analaytics">Google analytics tracking code</label></th>
<td>
<textarea class="code" style="width: 98%; font-size: 12px;" id="google_analaytics" rows="10" cols="60" name="google_analaytics">
<?php if ($google_analaytics){echo $google_analaytics;}?>
</textarea>
	Enter your analtics tracking code here
</td>
</tr>


</table>

<p class="submit">
<input id="kriesi_options" type="hidden" value="1" name="kriesi_options"/>
<input type="submit" name="Submit" value="Save Changes" /></p>

</form>

</div>
<?php
}

?>