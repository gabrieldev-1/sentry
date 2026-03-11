package com.sentry;

import oshi.software.os.OSProcess;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        System.out.println("Iniciando Sentry...");

        // Loop de atualização
        while (true) {
            try {
                // 1. Captura o estado atual do sistema
                SystemSnapShot snapshot = new SystemSnapShot();

                // 2. Limpa o terminal (ANSI Escape: \033[H move pro topo, \033[2J limpa tela)
                System.out.print("\033[H\033[2J");
                System.out.flush();

                // 3. Cabeçalho de Hardware
                System.out.println("====================================================");
                System.out.println("                SENTRY SYSTEM MONITOR               ");
                System.out.println("====================================================");
                System.out.printf("CPU Usage:    [%-20s] %.1f%%\n", 
                    generateProgressBar(snapshot.getCpuUsage()), snapshot.getCpuUsage());
                System.out.printf("Memory:       [%-20s] %.1f%%\n", 
                    generateProgressBar(snapshot.getMemoryUsage()), snapshot.getMemoryUsage());
                System.out.printf("CPU Temp:     %.1f°C\n", snapshot.getCpuTemp());
                System.out.println("====================================================\n");

                // 4. Tabela de Processos
                System.out.printf("%-7s %-10s %-7s %-20s\n", "PID", "%CPU", "%MEM", "NAME");
                System.out.println("----------------------------------------------------");
                
                for (OSProcess p : snapshot.getProcesses()) {
                    // Cálculo aproximado de CPU para o processo
                    double pCpu = 100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime();
                    // Cálculo de memória usando RSS
                    double pMem = (double) p.getResidentSetSize() / (1024 * 1024); // Convertendo para MB

                    System.out.printf("%-7d %-10.1f %-7.1f %-20s\n", 
                        p.getProcessID(), pCpu, pMem, p.getName());
                }

                // 5. Delay de 2 segundos para o próximo ciclo
                TimeUnit.SECONDS.sleep(2);

            } catch (InterruptedException e) {
                System.err.println("Monitoramento interrompido.");
                break;
            } catch (Exception e) {
                System.err.println("Erro ao coletar dados: " + e.getMessage());
                break;
            }
        }
    }

    /**
     * Gera uma barra de progresso simples em ASCII.
     */
    private static String generateProgressBar(double percentage) {
        int dots = (int) (percentage / 5); // 20 barras = 100%
        return "#".repeat(Math.max(0, dots));
    }
}