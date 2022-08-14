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

package org.viablespark.mapper;



import java.io.Serializable;

/**
 *
 * @author Martin Jamszolik
 */


public class AddressEntity implements Serializable {
	
	  
	private String addresszip;

	private String addressstate;

	private String address3;	

	private String address1;

	private String address2;
	
	private String addresscity;

	private static final long serialVersionUID = 1L;

	public AddressEntity() {
		super();
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

	
	public String getAddress3() {
		return this.address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
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

	
	public String getAddresscity() {
		return this.addresscity;
	}

	public void setAddresscity(String addresscity) {
		this.addresscity = addresscity;
	}

    @Override
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
