package com.rest_api_webservices.microservices.filtring;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FiltringController {
    @GetMapping("/filterSingleObject") // field2
    public MappingJacksonValue filterSingleObject(){
        SomeBean someBean = new SomeBean("val1", "val2", "val3");
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1");
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    @GetMapping("/filtering-mapping")
    public MappingJacksonValue filteringMapping() {
        SomeBean someBean = new SomeBean("val1", "val2", "val3");
        // Define which fields to include in the JSON
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");
        // Register the filter under the name "SomeBeanFilter"
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
        // Wrap the bean inside a MappingJacksonValue container
        MappingJacksonValue mapping = new MappingJacksonValue(someBean);
        mapping.setFilters(filters);
        /*
         Return the filtered JSON
        {
            "field1": "val1",
            "field2": "val2"
           }
         */
        return mapping;
    }


    @GetMapping("/filtering-list")
    public MappingJacksonValue filterListOfObjects() {
        List<SomeBean> list = Arrays.asList(new SomeBean("val1", "val2", "val3"), new SomeBean("val4", "val5", "val6"));
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2", "field3");
        FilterProvider filters  = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    @GetMapping("/unfiltered-list")
    public MappingJacksonValue returnFullList() {
        List<SomeBean> list = Arrays.asList(new SomeBean("val1", "val2", "val3"), new SomeBean("val4", "val5", "val6"));
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAll();
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }


}
