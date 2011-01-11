function toggleSelection(toggleId, checkBoxName) {
   var toggleCheckbox = document.getElementById(toggleId);
   var selectionCheckboxes = document.getElementsByName(checkBoxName);

   for(var i = 0; i < selectionCheckboxes.length; i++) {
      selectionCheckboxes[i].checked = toggleCheckbox.checked;
   }

   toggleDeleteButton(!toggleCheckbox.checked);
}

function checkDeleteButtonStatus(checkBoxName) {
   var selectionCheckboxes = document.getElementsByName(checkBoxName);
   var checked = false;

   for(var i = 0; i < selectionCheckboxes.length; i++) {
      if(selectionCheckboxes[i].checked) {
         checked = true;
         break;
      }
   }

   toggleDeleteButton(!checked);
}

function toggleDeleteButton(disabled) {
   var deleteButton = document.getElementById('deleteButton');
   deleteButton.disabled = disabled;
}