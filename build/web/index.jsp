<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="css/bootstrap.min.css">
		<link rel="shortcut icon" type="image/png" href="images/favicon.png"/>

        <title>Biblioteca</title>
    </head>
    <body>
		<nav class="navbar navbar-expand-lg navbar-dark bg-success text-white">
			<div class="container">
				<a class="navbar-brand" href="#">Biblioteca</a>
				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul class="navbar-nav mr-auto">
						<li class="nav-item active">
							<a class="nav-link" href="#">Início</a>
						</li>
						<li class="nav-item active">
							<a class="nav-link" href="categorias.jsp">Categorias</a>
						</li>
						<li class="nav-item active">
							<a class="nav-link" href="livros.jsp">Livros</a>
						</li>
						<li class="nav-item active">
							<a class="nav-link" href="emprestimos.jsp">Empréstimos</a>
						</li>
						<li class="nav-item active">
							<a class="nav-link" href="alunos.jsp">Alunos</a>
						</li>
						<li class="nav-item active">
							<a class="nav-link" href="usuarios.jsp">Usuários</a>
						</li>
						<li class="nav-item active">
							<a class="nav-link" href="deslogar.jsp">Sair</a>
						</li>
					</ul>
				</div>
			</div>
		</nav>
        <header>
            <div class="jumbotron jumbotron-fluid">
                <div class="container">
					<h1>Seja Bem-Vindo!</h1>
					<p class="lead">Este é o Sistema de Gestão da Biblioteca do IFRS. Navegue pelo menu superior nos itens desejados.</p>
                </div>
            </div>
        </header>
        <footer class="container mt-5">
            <hr>
            <div class="row">
                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                    <p class="text-muted text-center">Desenvolvido por Alan Possamai & Eduardo Schenato dos Santos &copy; 2018</p>
                </div>
            </div>
        </footer>
        <script src="js/jquery.js"></script>
        <script src="js/popper.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
