import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Viagem> historicoViagens = new ArrayList<>();
    private static Usuario usuarioLogado = null;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean sair = false;
        
        while (!sair) {
            if (usuarioLogado == null) {
                exibirMenuInicial();
            } else {
                exibirMenuPrincipal();
            }
            
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer do teclado
            
            if (usuarioLogado == null) {
                switch (opcao) {
                    case 1:
                        cadastrarUsuario();
                        break;
                    case 2:
                        fazerLogin();
                        break;
                    case 3:
                        sair = true;
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } else {
                switch (opcao) {
                    case 1:
                        solicitarViagem();
                        break;
                    case 2:
                        exibirHistoricoViagens();
                        break;
                    case 3:
                        exibirPerfil();
                        break;
                    case 4:
                        avaliarViagem();
                        break;
                    case 5:
                        fazerLogout();
                        break;
                    case 6:
                        sair = true;
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        }
        
        scanner.close();
    }
    
    public static void exibirMenuInicial() {
        System.out.println("===== Bem-vindo ao DriverU =====");
        System.out.println("Selecione uma opção:");
        System.out.println("1. Cadastrar-se");
        System.out.println("2. Fazer login");
        System.out.println("3. Sair");
    }
    
    public static void exibirMenuPrincipal() {
        System.out.println("===== Menu Principal =====");
        System.out.println("Selecione uma opção:");
        System.out.println("1. Solicitar viagem");
        System.out.println("2. Ver histórico de viagens");
        System.out.println("3. Ver perfil");
        System.out.println("4. Avaliar viagem");
        System.out.println("5. Fazer logout");
        System.out.println("6. Sair");
    }
    
    public static void cadastrarUsuario() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("===== Cadastro de Usuário =====");
        System.out.println("Digite seu nome:");
        String nome = scanner.nextLine();
        
        System.out.println("Digite seu email:");
        String email = scanner.nextLine();
        
        System.out.println("Digite sua senha:");
        String senha = scanner.nextLine();
        
        Usuario novoUsuario = new Usuario(nome, email, senha);
        usuarios.add(novoUsuario);
        
        System.out.println("Cadastro realizado com sucesso!");
        scanner.close();
    }
    
    public static void fazerLogin() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("===== Login =====");
        System.out.println("Digite seu email:");
        String email = scanner.nextLine();
        
        System.out.println("Digite sua senha:");
        String senha = scanner.nextLine();
        
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                usuarioLogado = usuario;
                System.out.println("Login realizado com sucesso!");
                return;
            }
        }
        
        System.out.println("Email ou senha incorretos. Tente novamente.");
        scanner.close();
    }
    
    public static void fazerLogout() {
        usuarioLogado = null;
        System.out.println("Logout realizado com sucesso!");
    }
    
    public static void solicitarViagem() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("===== Solicitar Viagem =====");
        System.out.println("Digite o local de partida:");
        String partida = scanner.nextLine();
        
        System.out.println("Digite o local de destino:");
        String destino = scanner.nextLine();
        
        double distancia = calcularDistancia(partida, destino);
        double preco = calcularPreco(distancia);
        
        Viagem novaViagem = new Viagem(partida, destino, distancia, preco);
        historicoViagens.add(novaViagem);
        
        System.out.println("Viagem solicitada com sucesso!");
        scanner.close();
    }
    
    public static void exibirHistoricoViagens() {
        System.out.println("===== Histórico de Viagens =====");
        
        if (historicoViagens.isEmpty()) {
            System.out.println("Nenhuma viagem encontrada.");
        } else {
            for (Viagem viagem : historicoViagens) {
                System.out.println("Partida: " + viagem.getPartida());
                System.out.println("Destino: " + viagem.getDestino());
                System.out.println("Distância: " + viagem.getDistancia() + " km");
                System.out.println("Preço: R$" + viagem.getPreco());
                System.out.println("-----------");
            }
        }
    }
    
    public static void exibirPerfil() {
        System.out.println("===== Perfil =====");
        
        System.out.println("Nome: " + usuarioLogado.getNome());
        System.out.println("Email: " + usuarioLogado.getEmail());
    }
    
    public static void avaliarViagem() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("===== Avaliar Viagem =====");
        System.out.println("Selecione a viagem que deseja avaliar:");
        
        if (historicoViagens.isEmpty()) {
            System.out.println("Nenhuma viagem disponível para avaliação.");
        } else {
            for (int i = 0; i < historicoViagens.size(); i++) {
                Viagem viagem = historicoViagens.get(i);
                System.out.println((i + 1) + ". Partida: " + viagem.getPartida() + ", Destino: " + viagem.getDestino());
            }
            
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer do teclado
            
            if (opcao > 0 && opcao <= historicoViagens.size()) {
                Viagem viagemSelecionada = historicoViagens.get(opcao - 1);
                System.out.println("Digite sua avaliação (de 1 a 5):");
                int avaliacao = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer do teclado
                
                if (avaliacao >= 1 && avaliacao <= 5) {
                    viagemSelecionada.setAvaliacao(avaliacao);
                    System.out.println("Avaliação realizada com sucesso!");
                } else {
                    System.out.println("Avaliação inválida.");
                }
            } else {
                System.out.println("Opção inválida.");
            }
        }
        
        scanner.close();
    }
    
    public static double calcularDistancia(String partida, String destino) {
        // Lógica para calcular a distância entre dois locais (pode ser uma API externa, por exemplo)
        return 10.5; // Valor de exemplo
    }
    
    public static double calcularPreco(double distancia) {
        // Lógica para calcular o preço com base na distância
        return distancia * 2.5; // Valor de exemplo
    }
}

class Usuario {
    private String nome;
    private String email;
    private String senha;
    
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getSenha() {
        return senha;
    }
}

class Viagem {
    private String partida;
    private String destino;
    private double distancia;
    private double preco;
    private int avaliacao;
    
    public Viagem(String partida, String destino, double distancia, double preco) {
        this.partida = partida;
        this.destino = destino;
        this.distancia = distancia;
        this.preco = preco;
    }
    
    public String getPartida() {
        return partida;
    }
    
    public String getDestino() {
        return destino;
    }
    
    public double getDistancia() {
        return distancia;
    }
    
    public double getPreco() {
        return preco;
    }
    
    public int getAvaliacao() {
        return avaliacao;
    }
    
    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }
}
