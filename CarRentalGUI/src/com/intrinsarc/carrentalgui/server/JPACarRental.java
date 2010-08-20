package com.intrinsarc.carrentalgui.server;

import java.util.*;

import org.hibernate.*;
import org.hibernate.cfg.*;

import com.intrinsarc.backbone.runtime.api.*;
import com.intrinsarc.backbone.runtime.implementation.*;
import com.intrinsarc.cars.*;

public class JPACarRental
{
// start generated code
// attributes
// required ports
	private com.intrinsarc.backbone.runtime.api.ICreate create;
	private java.util.List<com.intrinsarc.cars.IRentalCarDetails> cars = new java.util.ArrayList<com.intrinsarc.cars.IRentalCarDetails>();
	private java.util.List<com.intrinsarc.cars.IRenterDetails> renters = new java.util.ArrayList<com.intrinsarc.cars.IRenterDetails>();
// provided ports
	private IRentalServiceServiceImpl service_IRentalServiceProvided = new IRentalServiceServiceImpl();
// setters and getters
	public void setCreate_ICreate(com.intrinsarc.backbone.runtime.api.ICreate create) { this.create = create; }
	public void setCars_IRentalCarDetails(com.intrinsarc.cars.IRentalCarDetails cars, int index) { PortHelper.fill(this.cars, cars, index); }
	public void removeCars_IRentalCarDetails(com.intrinsarc.cars.IRentalCarDetails cars) { PortHelper.remove(this.cars, cars); }
	public void setRenters_IRenterDetails(com.intrinsarc.cars.IRenterDetails renters, int index) { PortHelper.fill(this.renters, renters, index); }
	public void removeRenters_IRenterDetails(com.intrinsarc.cars.IRenterDetails renters) { PortHelper.remove(this.renters, renters); }
	public com.intrinsarc.carrentalgui.client.IRentalService getService_IRentalService(Class<?> required) { return service_IRentalServiceProvided; }
// end generated code

	public static void main(String args[])
	{
		Session session = null;

		try
		{
			// This step will read hibernate.cfg.xml and prepare hibernate for use
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			session = sessionFactory.openSession();

			System.out.println("$$ creating car");
			RentalCarDetails rcd = new RentalCarDetails();
			rcd.setModel(new Attribute<String>("Porsche"));
			rcd.setPurchased(new Attribute<Date>(new Date(105, 10, 10)));
			
			RenterDetails renter = new RenterDetails();
			renter.setRenterName(new Attribute<String>("Andrew"));
			rcd.setRenter_IRenterDetails(renter.getDetails_IRenterDetails(null));
			
			System.out.println("$$ about to save...");
			Transaction tx = session.beginTransaction();
			session.save(rcd);
			tx.commit();
			System.out.println("$$ saved car");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (session != null)
				session.close();
		}
	}

	private class IRentalServiceServiceImpl implements com.intrinsarc.carrentalgui.client.IRentalService
	{
		public void createRentalCar(String model)
		{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("model", model);
			params.put("purchased", new Date());
			create.create(params);
		}

		public void rent(int car, String renter)
		{
			renters.get(car).setRenter(renter);
		}
		
		public void returnRental(int car)
		{
			renters.get(car).setRenter(null);
		}

		public String[] getCars()
		{
			String[] newCars = new String[cars.size()];
			int lp = 0;
			for (IRentalCarDetails r : cars)
			{
				newCars[lp] = (r.isRented() ? ">" : "") + r.getModel() + "|" + renters.get(lp).getRenter();
				lp++;
			}
			return newCars;
		}
	}
}
