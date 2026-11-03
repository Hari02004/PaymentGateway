<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Payment Failed</title>

    <style>
        body{
            font-family: Arial, sans-serif;
            background:#f5f5f5;
            display:flex;
            justify-content:center;
            align-items:center;
            height:100vh;
            margin:0;
        }

        .card{
            width:450px;
            background:#fff;
            padding:30px;
            border-radius:10px;
            box-shadow:0 4px 15px rgba(0,0,0,.2);
            text-align:center;
        }

        .icon{
            font-size:60px;
            color:red;
        }

        h2{
            color:#d32f2f;
        }

        p{
            color:#555;
            font-size:16px;
        }

        .btn{
            display:inline-block;
            margin-top:20px;
            padding:12px 25px;
            background:#1976d2;
            color:white;
            text-decoration:none;
            border-radius:5px;
        }

        .btn:hover{
            background:#1259a7;
        }
    </style>

    <script>
        let seconds = 5;

        function startTimer() {
            const timer = setInterval(function () {
                document.getElementById("count").innerText = seconds;
                seconds--;

                if (seconds < 0) {
                    clearInterval(timer);
                    window.location.href = "http://localhost:4200/";
                }
            }, 1000);
        }

        window.onload = startTimer;
    </script>

</head>

<body>

<div class="card">
    <div class="icon">❌</div>

    <h2>Payment Failed</h2>

    <p>${errorMessage}</p>

    <p>Redirecting to the home page in <span id="count">5</span> seconds...</p>

    <a class="btn" href="http://localhost:4200/">Go to Home</a>
</div>

</body>
</html>