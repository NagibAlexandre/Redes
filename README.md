# 🗳️ Sistema de Votação em Tempo Real

Este é um sistema de votação em tempo real que permite múltiplos clientes votarem simultaneamente, com atualizações em tempo real dos resultados. O sistema utiliza comunicação TCP para envio de votos e UDP para broadcast de atualizações.

## 📁 Estrutura do Projeto

### 🏗️ Classes Principais

#### 📊 Modelo

- **Enquete**: Classe central que gerencia o estado da votação
  - Gerencia o tempo de abertura e duração
  - Controla o status da enquete (aberta/fechada)
  - Mantém a lista de candidatos e seus votos
  - Implementa lógica de fechamento automático baseado no tempo

#### 💻 Cliente

- **ClienteTCP**: Gerencia a comunicação TCP com o servidor

  - Envia votos para o servidor
  - Obtém informações iniciais da enquete
  - Trata respostas do servidor

- **ClienteUDP**: Gerencia a comunicação UDP para atualizações em tempo real
  - Recebe broadcasts do servidor
  - Atualiza a interface do usuário com novos dados
  - Implementa listener para notificações de atualização

#### 🖥️ Servidor

- **ServidorTCP**: Gerencia conexões TCP e processamento de votos

  - Aceita conexões de múltiplos clientes
  - Processa votos e atualiza contadores
  - Envia respostas aos clientes

- **ServidorUDP**: Gerencia broadcast de atualizações
  - Envia atualizações periódicas para todos os clientes
  - Mantém os clientes sincronizados com o estado atual

#### 🎨 Interface

- **TelaVotar**: Interface gráfica do cliente
  - Exibe lista de candidatos e seus votos
  - Permite seleção e envio de votos
  - Atualiza em tempo real com novos resultados

## ⚙️ Funcionalidades

### 🎯 Sistema de Votação

- Múltiplos clientes podem votar simultaneamente
- Interface gráfica intuitiva com cards para cada candidato
- Exibição em tempo real do candidato mais votado
- Contagem automática de votos
- Fechamento automático da enquete após o tempo de duração

### ⏰ Controle de Tempo

- Tempo de abertura configurável
- Duração da votação configurável
- Fechamento automático quando o tempo expira

### 🔄 Atualizações em Tempo Real

- Broadcast UDP para todos os clientes
- Atualização automática da interface
- Sincronização de votos entre todos os clientes
- Exibição do status atual da enquete

## 🔄 Fluxo de Comunicação

### 📡 TCP (Transmissão de Votos)

1. Cliente estabelece conexão TCP com o servidor
2. Cliente envia voto para um candidato
3. Servidor processa o voto e atualiza contadores
4. Servidor envia confirmação ao cliente
5. Conexão TCP é fechada

### 📢 UDP (Atualizações em Tempo Real)

1. Servidor UDP envia broadcast periódico (a cada 1 segundo)
2. Todos os clientes UDP recebem as atualizações
3. Clientes atualizam suas interfaces com os novos dados
4. Processo continua até o fechamento da enquete

## 🚀 Como Executar

### 🖥️ Servidor

1. Execute a classe `ServidorTCP`
2. O servidor iniciará nas portas:
   - TCP: 9871
   - UDP: 9872

### 💻 Cliente

1. Execute a classe `Main`
2. A interface gráfica será exibida
3. O cliente se conectará automaticamente ao servidor
4. Você pode executar múltiplas instâncias do cliente

## 📋 Requisitos

- Java 8 ou superior
- Interface gráfica Swing
- Conexão de rede para comunicação cliente-servidor

## ℹ️ Observações

- O sistema suporta múltiplos clientes simultâneos
- As atualizações são em tempo real via UDP
- A enquete fecha automaticamente após o tempo configurado
- Os votos são persistidos apenas em memória
