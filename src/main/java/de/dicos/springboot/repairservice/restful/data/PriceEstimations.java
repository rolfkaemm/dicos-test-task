package de.dicos.springboot.repairservice.restful.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import de.dicos.springboot.repairservice.restful.error.DicosBadRequestException;
import de.dicos.springboot.repairservice.restful.error.DicosInternalServerErrorException;
import de.dicos.springboot.repairservice.restful.error.DomainServiceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PriceEstimations {

	private List<PriceEstimation> priceEstimations;

	@PostConstruct
    void initialize() {
		InputStream in = getClass().getResourceAsStream("/price_estimations.csv");
    	
        CsvMapper csvMapper = new CsvMapper();

        CsvSchema csvSchema = csvMapper
                .typedSchemaFor(PriceEstimation.class)
                .withHeader()
                .withColumnSeparator(',')
                .withComments();
		try {

			MappingIterator<PriceEstimation> priceEstimationIter = csvMapper
					.readerWithTypedSchemaFor(PriceEstimation.class).with(csvSchema).readValues(in);

			priceEstimations = priceEstimationIter.readAll();

			priceEstimations.forEach(p -> {
				log.info(p.toString());
				p.setCarModel(p.getCarModel().toLowerCase());
				p.setRepairAction(p.getRepairAction().toLowerCase());
			});
		} catch (IOException ex) {
			throw new DomainServiceException("Could not load price_estimations file.", ex);
		}
	}
	
	public PriceEstimation findPreisEstimation(String carModel, String repairAction) {
		return priceEstimations.stream().filter(
				p -> p.getCarModel().equalsIgnoreCase(carModel) && p.getRepairAction().equalsIgnoreCase(repairAction))
				.findAny().orElseThrow(() -> new DicosBadRequestException("Could not find carModel or repairAction"));
	}
	
}
