package com.Irrigation.SAIAFarming;

import com.Irrigation.SAIAFarming.controller.FarmController;
import com.Irrigation.SAIAFarming.controller.FarmerController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyResourceConfig extends ResourceConfig {
   public JerseyResourceConfig() {
        register(FarmerController.class);
        register(FarmController.class);


   }
}
