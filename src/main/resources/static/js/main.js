$(document).ready(function() {
  let inputs = document.querySelectorAll('.pretty-input-file');
  Array.prototype.forEach.call(inputs, function(input)
  {
    let label = input.nextElementSibling, labelVal = label.innerHTML;
    input.addEventListener('change', function(e)
    {
      let fileName = '';
      if(this.files && this.files.length > 1)
      {
        fileName = (this.getAttribute('data-multiple-caption') || '').replace('{count}', this.files.length);
      }
      else
      {
        fileName = e.target.value.split('\\').pop();
      }
      if(fileName)
      {
        label.querySelector('span').innerHTML = fileName;
      }
      else
      {
        label.innerHTML = labelVal;
      }
    });
    input.addEventListener('focus', function()
    {
      input.classList.add('has-focus');
    });
    input.addEventListener('blur', function()
    {
      input.classList.remove('has-focus');
    });
  });

  $('.clickable-row').on('click', function(){
    const data = $(this).attr('data-xml');
    copyToClipboard(data)
    swal({
           title:  'Просмотр XML (Данные уже скопированы в буфер обмена!) ',
           text: formatXml(data),
           type: 'info',
           html: true,
           showCancelButton: true,
           buttonsStyling: false,
           confirmButtonClass: 'btn',
           confirmButtonText: 'Ok',
           background: 'rgba(0, 0, 0, 0.96)'
         });
  });

  setTimeout(function() { $(".app-loading").fadeOut("slow");}, 200)

  resizeTable();

  window.onresize = function(event)
  {
    console.log("onresize");
    resizeTable();
  };

});

function resizeTable()
{
  $(".main-table").height($(window).height() - $('.toolbar').height() - $('.actions-panel').height() - 150);
}

function showSnackPopup(message, key)
{
  if(message && message.length > 0)
  {
    const x = document.getElementById(key);
    if(x)
    {
      x.className = "snackbar show";
      setTimeout(function() { x.className = x.className.replace("show", ""); }, 3000);
    }
  }
}

