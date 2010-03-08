/*
 * Copyright 2009-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.freedom.gj.example.jpa;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author Martin Jamszolik
 */

@Entity
public class AddressEntity implements Serializable {
	
	@Id	
	@GeneratedValue(strategy=GenerationType.AUTO )    
	private String addressid;

	private String createdby;

	private String addresszip;

	private String addressstate;

	@Temporal(TIMESTAMP)
	private Timestamp createddate;

	private String address3;

	private String updatedby;

	private String address1;

	private String address2;

	@Temporal(TIMESTAMP)
	private Timestamp updateddate;

	private String addresscity;

	private static final long serialVersionUID = 1L;

	public AddressEntity() {
		super();
	}

	public String getAddressid() {
		return this.addressid;
	}

	public void setAddressid(String addressid) {
		this.addressid = addressid;
	}

	public String getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getAddresszip() {
		return this.addresszip;
	}

	public void setAddresszip(String addresszip) {
		this.addresszip = addresszip;
	}

	public String getAddressstate() {
		return this.addressstate;
	}

	public void setAddressstate(String addressstate) {
		this.addressstate = addressstate;
	}

	public Timestamp getCreateddate() {
		return this.createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

	public String getAddress3() {
		return this.address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public Timestamp getUpdateddate() {
		return this.updateddate;
	}

	public void setUpdateddate(Timestamp updateddate) {
		this.updateddate = updateddate;
	}

	public String getAddresscity() {
		return this.addresscity;
	}

	public void setAddresscity(String addresscity) {
		this.addresscity = addresscity;
	}

    public String toString(){
          StringBuilder sb = new StringBuilder(address1);

        sb.append(address2 == null ? "" : " " + address2);
        sb.append(address3 == null ? "" : " " + address3);
        sb.append(addresscity == null ? "" : " " + addresscity);
        sb.append(addressstate == null ? "" : ", " + addressstate);
        sb.append(addresszip == null ? "" : " " + addresszip);

        return sb.toString();
    }
	

}
