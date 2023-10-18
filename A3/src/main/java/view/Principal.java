package view;

import java.util.Scanner;

import dao.PlataformaDAO;
import dao.SerieDAO;
import dao.UsuarioDAO;
import model.Plataforma;
import model.Serie;
import model.Status;
import model.Usuario;

public class Principal {

	public static void main(String[] args) {

		UsuarioDAO usuarioDao = new UsuarioDAO();
		PlataformaDAO platDao = new PlataformaDAO();
		SerieDAO serieDao = new SerieDAO();
		Usuario usuario = new Usuario();
		Plataforma plat = new Plataforma();
		Status status = null;
		Serie serie = new Serie();
		int resp;
		boolean respValida;
		boolean platValida;
		boolean statusValido;
		boolean infoValida;
		boolean serieValida;
		boolean loginValido = false;
		Scanner entrada = new Scanner(System.in);

		do {
			System.out.println("Bem vindo ao SerieList! \n\nEscolha uma opção: \n1- Realizar Cadastro \n2- Fazer Login"
					+ "\n3- Fechar aplicativo");
			resp = entrada.nextInt();

			switch (resp) {
			case 1: // Case 1 Realiza Cadastro e salva no banco!

				do {
					System.out.print("\nInforme seu login: ");
					usuario.setLogin(entrada.next());

					System.out.print("Informe sua senha: ");
					usuario.setSenha(entrada.next());

					entrada.nextLine();
					System.out.print("Informe seu nome: ");
					usuario.setNome(entrada.nextLine());

					System.out.print("Informe seu email: ");
					usuario.setEmail(entrada.next());

				} while (!usuarioDao.salvarUsuario(usuario));

				loginValido = false;
				break;

			case 2: // Case 2 Realiza Login!
				do {
					System.out.print("\nInforme seu login: ");
					usuario.setLogin(entrada.next());

					System.out.print("Informe sua senha: ");
					usuario.setSenha(entrada.next());
				} while (!usuarioDao.login(usuario));
				usuario.setId(usuarioDao.obterId(usuario));
				loginValido = true;
				break;

			case 3: // Case 3 Encerra a aplicação!
				loginValido = false;
				break;
			default:
				System.out.println("Opção inválida, tente novamente!");

			}

			// INTERFACE APÓS REALIZAR LOGIN

			if (loginValido) {
				do {
					System.out.println(
							"\nBem vindo ao SerieList! \n\nEscolha uma opção:" + "\n1- Adicionar Série \n2- Remover Série"
									+ "\n3- Ver todas as Séries \n4- Gerenciar plataformas\n5- Sair da sua conta");
					resp = entrada.nextInt();

					switch (resp) {
					case 1: // Case 1 Cria série e salva no banco!

						entrada.nextLine();
						System.out.print("\nInforme o nome da nova série: ");
						serie.setNome(entrada.nextLine());

						System.out.print("Informe quantas temporadas ela possui: ");
						serie.setTemporadas(entrada.nextInt());

						System.out.print("Informe a data de lançamento(DD/MM/AAAA): ");
						serie.setLancamento(entrada.next());

						do { // Adiciona plataforma a série e dá a opção de criar uma e salvar no banco!
							platValida = false;
							System.out.println("Informe a plataforma da série: ");
							plat.verPlataforma(usuario);
							System.out.println("Para adicionar uma nova plataforma digite \"nova\"");
							String escolhaPlat = entrada.next();
							switch (escolhaPlat.toLowerCase()) {
							case "nova":
								System.out.print("Informe o nome da plataforma: ");
								plat.setNome(entrada.next());
								plat.setUsuario(usuario);
								platDao.salvarPlataforma(plat);
								break;
							default:
								plat.setNome(escolhaPlat);
								plat.setUsuario(usuario);
								plat.setId(platDao.obterId(plat));
								if (plat.getId() == 0) {
									platValida = false;
									System.out.println("Plataforma inválida, tente novamente!");
								} else {
									serie.setPlataforma(plat);
									platValida = true;
								}
							}
						} while (!platValida);

						do { // adiciona status a série!
							statusValido = false;
							System.out.println("Informe o status da série: \nEscolha uma das opções:"
									+ "\n1- Quero Assistir \n2- Assistindo \n3- Concluida");
							resp = entrada.nextInt();
							switch (resp) {
							case 1:
								status = Status.QUERO_ASSISTIR;
								statusValido = true;
								break;
							case 2:
								status = Status.ASSISTINDO;
								statusValido = true;
								break;
							case 3:
								status = Status.CONCLUIDA;
								statusValido = true;
								break;
							default:
								System.out.println("Opção inválida, tente novamente!");
								statusValido = false;
							}
						} while (!statusValido);
						serie.setStatus(status);
						serie.setUsuario(usuario);
						serieDao.salvarSerie(serie);

						break;
					case 2: // Remover série do banco!
						do {
							serieValida = false;
							serie.verSeries(usuario);
							System.out.println("Digite o nome da série que quer remover: ");
							serie.setNome(entrada.next());
							serie.setId(serieDao.obterId(serie, usuario));
							if(serie.getId() == 0) {
								serieValida = false;
								System.out.println("Série não encontrada, tente novamente!");
							}else {
								serieDao.removerSerie(serie);
								serieValida = true;
							}
						} while (!serieValida);
						break;
					case 3: // Lista as séries e dá a opção de ver os detalhes!
						do {
							respValida = false;
							serie.verSeries(usuario);
							System.out.println("\nVocê deseja ver os detalhes de alguma série? \n1- Sim \n2- Não");
							resp = entrada.nextInt();
							switch (resp) {
							case 1:
								entrada.nextLine();
								
								do {
									serieValida = false;
									System.out.print("\nDigite o nome da série: ");
									serie.setNome(entrada.nextLine());
									serie.setId(serieDao.obterId(serie, usuario));
									if(serie.getId() == 0) {
										serieValida = false;
										System.out.println("Série não encontrada, tente novamente!");
									}else {
										serieValida = true;
										serie = serieDao.detalhesSerie(serie);
										System.out.println(serie);
									}
								}while(!serieValida);

								do {
									respValida = false;
									System.out.println("\nDeseja alterar alguma informação da série? \n1- Sim \n2- Não");
									resp = entrada.nextInt();
									switch (resp) {
									case 1:
										do {
											infoValida = false;
											System.out.println("\nQual informação você deseja alterar?");
											String informacao = entrada.next().toLowerCase();
											switch (informacao) {
											case "nome":
												entrada.nextLine();
												System.out.println("\nDigite o novo nome da série: ");
												serie.setNome(entrada.nextLine());
												serieDao.alterarNomeSerie(serie);
												System.out.println(serieDao.detalhesSerie(serie));
												infoValida = true;
												break;
											case "temporadas":
												System.out.println("\nDigite a nova quantidade de temporadas da série: ");
												serie.setTemporadas(entrada.nextInt());
												serieDao.alterarTemporadasSerie(serie);
												System.out.println(serieDao.detalhesSerie(serie));
												infoValida = true;
												break;
											case "lançamento":
												System.out.println("\nInforme a nova data de lançamento(DD/MM/AAAA): ");
												serie.setLancamento(entrada.next());
												serieDao.alterarLancamentoSerie(serie);
												System.out.println(serieDao.detalhesSerie(serie));
												infoValida = true;
												break;
											case "status": // alterar status da série!
												do {
													statusValido = false;
													System.out.println(
															"\nInforme o novo status da série: \nEscolha uma das opções:"
																	+ "\n1- Quero Assistir \n2- Assistindo \n3- Concluida");
													resp = entrada.nextInt();
													switch (resp) {
													case 1:
														status = Status.QUERO_ASSISTIR;
														statusValido = true;
														break;
													case 2:
														status = Status.ASSISTINDO;
														statusValido = true;
														break;
													case 3:
														status = Status.CONCLUIDA;
														statusValido = true;
														break;
													default:
														System.out.println("\nOpção inválida, tente novamente!");
														statusValido = false;
													}
												} while (!statusValido);
												serie.setStatus(status);
												serieDao.alterarStatusSerie(serie);
												System.out.println(serieDao.detalhesSerie(serie));
												infoValida = true;
												break;
											case "plataforma": // Altera a plataforma da série e dá a opção de criar uma e
																// salvar no
																// banco!
												do {
													platValida = false;
													System.out.println("\nInforme a nova plataforma da série: ");
													plat.verPlataforma(usuario);
													System.out
															.println("Para adicionar uma nova plataforma digite \"nova\"");
													String escolhaPlat = entrada.next();
													switch (escolhaPlat) {
													case "nova":
														System.out.print("\nInforme o nome da plataforma: ");
														plat.setNome(entrada.next());
														plat.setUsuario(usuario);
														platDao.salvarPlataforma(plat);
														break;
													default:
														plat.setNome(escolhaPlat);
														plat.setId(platDao.obterId(plat));
														plat.setUsuario(usuario);
														serie.setPlataforma(plat);
														if (plat.getId() == 0) {
															platValida = false;
															System.out.println("\nPlataforma inválida, tente novamente!");
														} else {
															platValida = true;
														}
													}
												} while (!platValida);
												serie.setPlataforma(plat);
												serieDao.alterarPlataformaSerie(serie);
												System.out.println(serieDao.detalhesSerie(serie));
												infoValida = true;
												break;
											default:
												infoValida = false;
												System.out.println("\nInformação inválida, certifique-se de escrever o nome"
														+ "\nda informação como está escrito nos detalhes da série!");
											}
										} while (!infoValida);
										respValida = true;
										break;
									case 2:
										respValida = true;
										break;
									default:
										System.out.println("\nOpção inválida, tente novamente!");
										respValida = false;
									}
								} while (!respValida);

								break;
							case 2: // Volta ao menu anterior!
								respValida = true;
								break;
							default:
								System.out.println("\nOpção inválida, tente novamente!");
							}
						}while(!respValida);

						break;
					case 4: // Gerencia as plataformas, adiciona ou remove!
						do {
							System.out.println("\nSuas plataformas: \n");
							plat.verPlataforma(usuario);
							System.out.println(
									"\nEscolha uma opção: \n1- Adicionar plataforma \n2- Remover plataforma \n3- Voltar");
							resp = entrada.nextInt();
							switch (resp) {
							case 1:
								System.out.print("Informe o nome da plataforma: ");
								plat.setNome(entrada.next());
								plat.setUsuario(usuario);
								platDao.salvarPlataforma(plat);

								break;
							case 2:
								do {
									platValida = false;
									System.out.println("\nAviso: Caso remova uma plataforma, todas as séries"
											+ "\nrelacionadas a essa plataforma serão removidas!\n");
									System.out.print("Informe o nome da plataforma: ");
									plat.setNome(entrada.next());
									plat.setId(platDao.obterId(plat));
									if (plat.getId() == 0) {
										platValida = false;
										System.out.println("\nNome de plataforma não encontrado, tente novamente!");
									} else {
										platValida = true;
										platDao.removerPlataforma(plat);
									}
								} while (!platValida);

								break;
							case 3: // volta pro menu anterior!
								break;
							default:
								System.out.println("\nOpção inválida, tente novamente!");
								resp = 0;

							}
						} while (resp != 3);
						break;
					case 5: // Encerra o programa!

						break;
					default:
						System.out.println("Opção inválida, tente novamente!");
					}

				} while (resp != 5);
				resp = 0;
			}
			System.out.println("");
		} while (resp != 3);
		System.out.println("Obrigado por usar o SerieList!");
		entrada.close();
	}
}
