<!DOCTYPE html>
<html>
<head>
  <title>Test page for nano</title>
    <!-- include lib -->
    <script src="assets/tpl/nano.js"></script>
    <!-- test data -->
    <script type="text/javascript">
        data = {
            user: {
                login: "tomek",
                first_name: "Thomas",
                last_name: "Mazur",
                account: {
                    status: "activex",
                    expires_at: "2009-12-31"
                }
            }
        }
    </script>

</head>
<body>


<p id="testLayout">nano("&lt;p&gt;Hello {user.first_name} {user.last_name}! Your account&nbsp;is &lt;strong&gt;{user.account.status}&lt;/strong&gt;&lt;/p&gt;", data)</p>


<script type="text/javascript">
    window.onload = function () {
        var x=nano("<p>Hello {user.first_name} {user.last_name}! Your account is <strong>{user.account.status}</strong></p>",data);
   		alert(x);
    }
</script>
</body>
</html>