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

package org.viablespark.example.pojo;

import java.io.Serializable;
import java.util.Date;
/**
 *
 * @author Martin Jamszolik
 */
public class Address implements Serializable {

    private static final long serialVersionUID = 4348764353373133379L;
    private String addressid;
    private String addressZip;
    private String addressZip2;
    private String addressState;
    private String address3;
    private String address1;
    private String address2;
    private String addressCity;
    private String county;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;

    public Address() {
    }

    public Address(String address1, String city, String state, String zip) {
        this.addressZip = zip;
        this.addressState = state;
        this.address1 = address1;
        this.addressCity = city;
    }

    public Address(String street) {
        address1 = street;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public String getAddressZip() {
        return addressZip;
    }

    public void setAddressZip(String addressZip) {
        this.addressZip = addressZip;
    }

    public String getAddressZip2() {
        return addressZip2;
    }

    public void setAddressZip2(String addressZip2) {
        this.addressZip2 = addressZip2;
    }

    public Address clone() {
        Address address = new Address();
        address.setAddress1(address1);
        address.setAddress2(address2);
        address.setAddress3(address3);
        address.setAddressCity(addressCity);
        address.setAddressState(addressState);
        address.setAddressZip(addressZip);
        address.setAddressZip2(addressZip2);
        address.setCounty(county);

        return address;
    }
    private StringBuilder toString = new StringBuilder();

    @Override
    public String toString() {
        toString.delete(0, toString.length());
        toString.append(address1).append(address2 == null ? "" : address2).append(address3 == null ? "" : address3).append(addressCity == null ? "" : "," + addressCity).append(addressState == null ? "" : "," + addressState).append(addressZip == null ? "" : "," + addressZip);

        return toString.toString();

    }

    public String toString(Boolean oneLineFormat) {

        if (!oneLineFormat) {
            return toString();
        }

        StringBuilder sb = new StringBuilder(address1);

        sb.append(address2 == null ? "" : " " + address2);
        sb.append(address3 == null ? "" : " " + address3);
        sb.append(addressCity == null ? "" : " " + addressCity);
        sb.append(addressState == null ? "" : ", " + addressState);
        sb.append(addressZip == null ? "" : " " + addressZip);

        return sb.toString();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Address)) {
            return false;
        }

        Address address = (Address) obj;
        if ((this.address1 != null && address.getAddress1() == null) || (this.address1 == null && address.getAddress1() != null) || (this.address1 != null && !this.address1.equalsIgnoreCase(address.getAddress1()))) {
            return false;
        } else if ((this.address2 != null && address.getAddress2() == null) || (this.address2 == null && address.getAddress2() != null) || (this.address2 != null && !this.address2.equalsIgnoreCase(address.getAddress2()))) {
            return false;
        } else if ((this.addressCity != null && address.getAddressCity() == null) || (this.addressCity == null && address.getAddressCity() != null) || (this.addressCity != null && !this.addressCity.equalsIgnoreCase(address.getAddressCity()))) {
            return false;
        } else if ((this.addressState != null && address.getAddressState() == null) || (this.addressState == null && address.getAddressState() != null) || (this.addressState != null && !this.addressState.equalsIgnoreCase(address.getAddressState()))) {
            return false;
        } else if ((this.addressZip != null && address.getAddressZip() == null) || (this.addressZip == null && address.getAddressZip() != null) || (this.addressZip != null && !this.addressZip.equalsIgnoreCase(address.getAddressZip()))) {
            return false;
        } else if ((this.addressZip2 != null && address.getAddressZip2() == null) || (this.addressZip2 == null && address.getAddressZip2() != null) || (this.addressZip2 != null && !this.addressZip2.equalsIgnoreCase(address.getAddressZip2()))) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int retVal = 17;
        retVal *= 37 + address1 == null ? 0 : address1.hashCode();
        return retVal;
    }
}
