package com.intrinsarc.carrentalgui.server;

import java.util.*;

import org.hibernate.*;
import org.hibernate.cfg.*;

import com.intrinsarc.backbone.runtime.api.*;
import com.intrinsarc.base.*;

public class HibernateCarRental
// start generated code
  // main port
 implements com.intrinsarc.carrentalgui.client.IRentalService, ILifecycle
{
  // required ports
	private com.intrinsarc.backbone.runtime.api.ICreate create;
	private java.util.List<com.intrinsarc.base.IRentalCarDetails> cars = new java.util.ArrayList<com.intrinsarc.base.IRentalCarDetails>();

  // port setters and getters
	public void setCreate(com.intrinsarc.backbone.runtime.api.ICreate create) { this.create = create; }
	public void setCar(com.intrinsarc.base.IRentalCarDetails cars, int index) { PortHelper.fill(this.cars, cars, index); }
	public void addCar(com.intrinsarc.base.IRentalCarDetails cars) { PortHelper.fill(this.cars, cars, -1); }
	public void removeCar(com.intrinsarc.base.IRentalCarDetails cars) { PortHelper.remove(this.cars, cars); }

// end generated code
	
	private Session session;
	public void afterInit()
	{
    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    session = sessionFactory.openSession();

    // get all the existing rental cars
    Transaction t = session.beginTransaction();
    cars.addAll(
    		session.createQuery("from RentalCarDetails").list());
    System.out.println("Found " + cars.size() + " existing car(s) in database");
    t.commit();
	}
	
	public void beforeDelete()
	{
    session.close();
	}

	public void createRentalCar(String model)
	{
		Transaction t = session.beginTransaction();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("model", model);
		params.put("purchased", new Date());
		create.create(params);
		session.save(cars.get(cars.size() - 1));
		t.commit();
	}

	public void rent(int car, String renter)
	{
		Transaction t = session.beginTransaction();
		cars.get(car).setRenterName(renter);
		t.commit();
	}
	
	public void returnRental(int car)
	{
		Transaction t = session.beginTransaction();
		cars.get(car).setRenterName("");
		t.commit();
	}

	public String[] getCars()
	{
		String[] newCars = new String[cars.size()];
		int lp = 0;
		for (IRentalCarDetails r : cars)
		{
			newCars[lp] = (r.isRented() ? ">" : "") + r.getModel() + "|" + r.getRenterName();
			lp++;
		}
		return newCars;
	}
}
