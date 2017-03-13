<h3>NEW TASK</h3>
<form id="newTaskForm" name="newTaskForm" action="rest/task" method="post", enctype="multipart/form-data">
    <!-- <input type="file" name="file" /><br /><br /> -->
    <div class="form-group">
    	<label for="algorithm" class="control-label">Algorithm</label>
    	<input type="text" name="algorithm" id="algorithm" />
    </div>
    <div class="form-group">
    	<label for="instance" class="control-label">Instance</label>
    	<input type="text" name="instance" />
    </div>
    <input type="submit" />
</form>
