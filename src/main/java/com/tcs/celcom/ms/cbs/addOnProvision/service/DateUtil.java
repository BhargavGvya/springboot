package com.tcs.celcom.ms.cbs.addOnProvision.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcs.celcom.ms.cbs.addOnProvision.intEnq.model.IntegrationEnquiryResDTO;
import com.tcs.celcom.ms.cbs.addOnProvision.intEnq.model.Product;

/*
 * Returns EffectiveTime and EndDateTime based on offeringID
 * 2060241 - 1am - 8am
 * 2060242 - 8am - 6pm
 * 2060243 & 2060244 - highest end date from customer's MI offerings
 */

@Component
public class DateUtil {

	protected String getEndDateTime(String offeringId,IntegrationEnquiryResDTO integrationEnquiryResponse) {
		String endDateTime = null;
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		
		if(offeringId.contentEquals("2060241"))
		{
			if(time.getHour()>0 && time.getHour()<8)
			{
				LocalTime localTime = LocalTime.of(8, 0, 0);
				LocalDateTime effectiveDateTime = LocalDateTime.of(date, localTime);
				endDateTime = effectiveDateTime.format(dateFormat);
			}
			else
			{
				LocalTime localTime = LocalTime.of(8, 0, 0);
				LocalDateTime effectiveDateTime = LocalDateTime.of(date.plusDays(1), localTime);
				endDateTime = effectiveDateTime.format(dateFormat);
			}
		}
		else if(offeringId.contentEquals("2060242"))
		{
			if(time.getHour()>0 && time.getHour()<16)
			{
				LocalTime localTime = LocalTime.of(18, 0, 0);
				LocalDateTime effectiveDateTime = LocalDateTime.of(date, localTime);
				endDateTime = effectiveDateTime.format(dateFormat);
			}
			else
			{
				LocalTime localTime = LocalTime.of(18, 0, 0);
				LocalDateTime effectiveDateTime = LocalDateTime.of(date.plusDays(1), localTime);
				endDateTime = effectiveDateTime.format(dateFormat);
			}
		}
		else if(offeringId.contentEquals("2060243"))
		{
			for(Map.Entry<String, String> entry: AddOnProvisionService.speedBoosterEligibleOfferings.entrySet())
			{
				List<Product> MiOfferingList = integrationEnquiryResponse.getSubscriberInfo().getProduct().stream().filter(product -> product.getId().contentEquals(entry.getKey())).collect(Collectors.toList());
				Optional<Product> max = MiOfferingList.stream().max(Comparator.comparing(Product::getExpiredDate));
				if(max.isPresent())
					endDateTime = max.get().getExpiredDate();
			}
		}
		else if(offeringId.contentEquals("2060244"))
		{
			for(Map.Entry<String, String> entry: AddOnProvisionService.hotspotEligibleOfferings.entrySet())
			{
				List<Product> MiOfferingList = integrationEnquiryResponse.getSubscriberInfo().getProduct().stream().filter(product -> product.getId().contentEquals(entry.getKey())).collect(Collectors.toList());
				Optional<Product> max = MiOfferingList.stream().max(Comparator.comparing(Product::getExpiredDate));
				if(max.isPresent())
					endDateTime = max.get().getExpiredDate();
			}
		}
		else
			endDateTime = null;
					
		return endDateTime;
	}

	protected String getEffectiveTime(String offeringId,IntegrationEnquiryResDTO integrationEnquiryResponse) {

		String effectiveTime = null;
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		
		if(offeringId.contentEquals("2060241"))
		{
			if(time.getHour()>0 && time.getHour()<8)
			{
				LocalTime localTime = LocalTime.of(1, 0, 0);
				LocalDateTime effectiveDateTime = LocalDateTime.of(date, localTime);
				effectiveTime = effectiveDateTime.format(dateFormat);
			}
			else
			{
				LocalTime localTime = LocalTime.of(1, 0, 0);
				LocalDateTime effectiveDateTime = LocalDateTime.of(date.plusDays(1), localTime);
				effectiveTime = effectiveDateTime.format(dateFormat);
			}
		}
		else if(offeringId.contentEquals("2060242"))
		{
			if(time.getHour()>0 && time.getHour()<16)
			{
				LocalTime localTime = LocalTime.of(8, 0, 0);
				LocalDateTime effectiveDateTime = LocalDateTime.of(date, localTime);
				effectiveTime = effectiveDateTime.format(dateFormat);
			}
			else
			{
				LocalTime localTime = LocalTime.of(8, 0, 0);
				LocalDateTime effectiveDateTime = LocalDateTime.of(date.plusDays(1), localTime);
				effectiveTime = effectiveDateTime.format(dateFormat);
			}
		}
		else if(offeringId.contentEquals("2060243"))
		{
			for(Map.Entry<String, String> entry: AddOnProvisionService.speedBoosterEligibleOfferings.entrySet())
			{
				List<Product> MiOfferingList = integrationEnquiryResponse.getSubscriberInfo().getProduct().stream().filter(product -> product.getId().contentEquals(entry.getKey())).collect(Collectors.toList());
				Optional<Product> max = MiOfferingList.stream().max(Comparator.comparing(Product::getEffectiveDate));
				if(max.isPresent())
					effectiveTime = max.get().getExpiredDate();
			}
		}
		else if(offeringId.contentEquals("2060244"))
		{
			for(Map.Entry<String, String> entry: AddOnProvisionService.hotspotEligibleOfferings.entrySet())
			{
				List<Product> MiOfferingList = integrationEnquiryResponse.getSubscriberInfo().getProduct().stream().filter(product -> product.getId().contentEquals(entry.getKey())).collect(Collectors.toList());
				Optional<Product> max = MiOfferingList.stream().max(Comparator.comparing(Product::getEffectiveDate));
				if(max.isPresent())
					effectiveTime = max.get().getEffectiveDate();
			}
		}
		else
			effectiveTime = null;
					
		return effectiveTime;
	}

}
