package com.xm.cryptorecommendationservice.service.reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.xm.cryptorecommendationservice.exception.CryptoNotFoundException;
import com.xm.cryptorecommendationservice.model.Crypto;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CryptoCsvReaderService implements CryptoReaderService {

    @Override
    public Map<String, List<Crypto>> readAll() {
        Map<String, List<Crypto>> allPrices = new HashMap<>();

        File folder = new File("src/main/resources/csvs");
        File[] files = folder.listFiles();

        assert files != null;
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith("_values.csv")) {
                String cryptoSymbol = file.getName().replace("_values.csv", "");
                List<Crypto> prices = readPrices(cryptoSymbol);
                allPrices.put(cryptoSymbol, prices);
            }
        }

        return allPrices;
    }

    @Override
    @SneakyThrows(CsvValidationException.class)
    public List<Crypto> readPrices(String cryptoSymbol) {
        List<Crypto> prices = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(ResourceUtils.getFile(getFilePath(cryptoSymbol))))) {
            String[] line;
            reader.readNext(); // Skip header line

            while ((line = reader.readNext()) != null) {
                Crypto Crypto = getCryptoFromFileLine(line);
                prices.add(Crypto);
            }
        } catch (FileNotFoundException e) {
            throw new CryptoNotFoundException("File not found with symbol name " + cryptoSymbol);
        } catch (IOException e) {
            throw new RuntimeException("General issue with reading file, please contact system admin");
        }

        return prices;
    }

    private Crypto getCryptoFromFileLine(String[] line) {
        LocalDateTime dateTime = getLocalDateTimeFromTimestamp(Long.parseLong(line[0]));
        String symbolName = line[1];
        BigDecimal price = new BigDecimal(line[2]);
        return new Crypto(dateTime, symbolName, price);
    }

    private LocalDateTime getLocalDateTimeFromTimestamp(Long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());
    }

    private String getFilePath(String cryptoSymbol) {
        return "classpath:csvs/" + cryptoSymbol + "_values.csv";
    }
}
