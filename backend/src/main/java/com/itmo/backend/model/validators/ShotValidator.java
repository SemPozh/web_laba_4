package com.itmo.backend.model.validators;

import com.itmo.backend.exceptions.ValidationException;
import jakarta.ejb.Stateless;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ShotValidator {
    private static final Set<Double> xValues= new HashSet<>(Arrays.asList(-4.0, -3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0));
    private static final Set<Integer> rValues= new HashSet<>(Arrays.asList(-4, -3, -2, -1, 0, 1, 2, 3, 4));

    public static void validateX(double x, boolean areaClick) throws ValidationException {
        try{
            if (!areaClick && !xValues.contains(x)){
                throw new ValidationException("X must be in set: {-4, -3, -2, -1, 0, 1, 2, 3, 4}");
            }
        } catch (NumberFormatException e){
            throw new ValidationException("X must be an integer!!");
        }
    }

    public static void validateY(double y, boolean areaClick) throws ValidationException{
        try{
            if (!areaClick && (y <= -3 || y >=3)){
                throw new ValidationException("Y must be in interval (-3, 3)");
            }
        } catch (NumberFormatException e){
            throw new ValidationException("Y must be a double!");
        }

    }

    public static void validateR(int r) throws ValidationException{
        try{
            if (!rValues.contains(r)){
                throw new ValidationException("R must be in set: {-4, -3, -2, -1, 0, 1, 2, 3, 4}");
            }
        } catch (NumberFormatException e){
            throw new ValidationException("R must be an integer!!");
        }
    }
}
