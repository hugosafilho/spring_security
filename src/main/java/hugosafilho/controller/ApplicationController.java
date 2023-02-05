package hugosafilho.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hugosafilho.business.ApplicationBusiness;

@Controller
public class ApplicationController {

	@Autowired
	ApplicationBusiness properties;

	@RequestMapping("/property")
	@ResponseBody
	public ResponseEntity<String> getProperty(@RequestParam(name = "name") String propertyName) {
		String propertyValue = null;

		try {
			propertyValue = properties.getProperty(propertyName);

			if (propertyValue == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(properties.getProperty(propertyName), HttpStatus.OK);
			}

		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
