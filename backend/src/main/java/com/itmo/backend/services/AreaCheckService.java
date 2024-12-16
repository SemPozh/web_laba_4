package com.itmo.backend.services;

import com.itmo.backend.exceptions.ValidationException;
import com.itmo.backend.model.entity.Shot;
import com.itmo.backend.model.validators.ShotValidator;
import jakarta.ejb.Stateless;

public class AreaCheckService {
    public static boolean calculateResult(double x, double y, int r, boolean byClick) throws ValidationException {
        ShotValidator.validateX(x, byClick);
        ShotValidator.validateY(y, byClick);
        ShotValidator.validateR(r);
        return (y>=(-0.5*x - (double) r/2) && x<=0 && y<=0)||(((Math.pow(x,2) + Math.pow(y,2)) <= Math.pow(r,2)/4) && y>=0 && x<=0)||(x>=0 && x<= r && y>=0 && y<= (double) r /2);
    }
}
