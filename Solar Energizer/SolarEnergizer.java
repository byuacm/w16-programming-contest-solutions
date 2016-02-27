package com.parivedasolutions.solarenergizer;
​
public class SolarEnergizer {
​
  
  public int computeSolarEnergy(int rating, int tilt, int azimuth, double shading) {
    
    double generated = rating*1.0;
    double tiltradians = Math.toRadians(Math.abs(30-tilt));
    double azimuthradians = Math.toRadians(Math.abs(180-azimuth));
    
    double tiltfactor = Math.cos(tiltradians) * 0.71;
    double azimuthfactor = Math.cos(azimuthradians);
    
    return (int) (generated * tiltfactor * azimuthfactor * shading * 365 * 5.26);
  }
}