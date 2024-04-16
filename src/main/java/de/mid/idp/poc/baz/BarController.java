package de.mid.idp.poc.baz;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bar")
public class BarController {
  @GetMapping
  public String baz() {return "bar";}
}
