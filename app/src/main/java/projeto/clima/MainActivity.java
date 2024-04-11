package org.example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.time.LocalDate;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        String url = "https://www.climatempo.com.br/previsao-do-tempo/cidade/558/saopaulo-sp";
        try {
            Document doc = Jsoup.connect(url).get();
            Element tempMinElement = doc.getElementById("min-temp-1");
            String tempMin = "";
            if (tempMinElement != null) {
                tempMin = tempMinElement.text();
            }

            Element tempMaxElement = doc.getElementById("max-temp-1");
            String tempMax = "";
            if (tempMaxElement != null) {
                tempMax = tempMaxElement.text();
            }

            Element chuvaInfo = doc.select("._margin-l-5").get(2);
            String[] chuvaData = chuvaInfo.text().split(" ");
            String chuvaMm = chuvaData[0];
            String chuvaPorcento = chuvaData[2].replace("%", "");
            Element qualidadeArElement = doc.select(".value").first();
            String qualidadeAr = "";
            if (qualidadeArElement != null) {
                qualidadeAr = qualidadeArElement.text().trim();
            }

            LocalDate date = LocalDate.now();
            int dia = date.getDayOfMonth();
            int mes = date.getMonthValue();
            int ano = date.getYear();

            System.out.println("Previsão de Hoje " + dia + "/" + mes + "/" + ano + "  em São Paulo - SP");
            System.out.println("Temperatura Máxima: " + tempMax);
            System.out.println("Temperatura Mínima: " + tempMin);
            System.out.println("Chuva: " + chuvaMm + " - " + chuvaPorcento + "%");
            if (Integer.parseInt(chuvaPorcento) > 60) {
                System.out.println("Grandes Possibilidades de chuva!");
            }
            System.out.println("Qualidade do ar: " + qualidadeAr);
        } catch (IOException e) {
            logger.error("Erro ao conectar ao URL: " + url, e);
        }
    }
}