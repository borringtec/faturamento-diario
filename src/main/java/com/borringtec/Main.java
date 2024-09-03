package com.borringtec;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("dados.json")));
            JSONArray jsonArray = new JSONArray(content);

            List<Double> valores = new ArrayList<>();
            double menorValor = Double.MAX_VALUE;
            double maiorValor = Double.MIN_VALUE;
            double somaValores = 0;
            int diasComFaturamento = 0;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                double valor = obj.getDouble("valor");

                if (valor > 0) {
                    valores.add(valor);
                    somaValores += valor;
                    if (valor < menorValor) {
                        menorValor = valor;
                    }
                    if (valor > maiorValor) {
                        maiorValor = valor;
                    }
                }
            }

            double mediaMensal = somaValores / valores.size();

            for (double valor : valores) {
                if (valor > mediaMensal) {
                    diasComFaturamento++;
                }
            }

            NumberFormat formatador = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

            System.out.println("Menor valor de faturamento: " + formatador.format(menorValor));
            System.out.println("Maior valor de faturamento: " + formatador.format(maiorValor));
            System.out.println("Número de dias com faturamento acima da média mensal: " + diasComFaturamento);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo JSON: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao processar os dados: " + e.getMessage());
        }
    }
}
