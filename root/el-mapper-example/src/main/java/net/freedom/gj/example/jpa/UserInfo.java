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
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Martin Jamszolik
 */
@Entity
@Table(name="USERINFO")
@NamedQueries({
	@NamedQuery(name="UserInfo.getLoginId", query="SELECT u.loginid FROM UserInfo u WHERE u.userid = :userId"),
	@NamedQuery(name="UserInfo.fromloginid", query="SELECT u FROM UserInfo u WHERE u.loginid = :loginid")
})
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String userid;

	private int vendorid;

	private String loginid;
	
	private String updatedby;

	private String middlename;

	private String lastname;

	private String changepwd;

	private String firstname;

	private String ufirstname;

	private String salutationname;

	private String pwd;

	private int insgroupid;

	
	private int companyid;

	private String suffixname;

	private String dbaname;

	private String ulastname;

	private String email;

	private String status;

	private int insagencyid;

	private String nametype;


    private List<GroupEntity> groups;

    public List<GroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupEntity> groups) {
        this.groups = groups;
    }

    
	public UserInfo() {
		super();
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getVendorid() {
		return this.vendorid;
	}

	public void setVendorid(int vendorid) {
		this.vendorid = vendorid;
	}

	public String getLoginid() {
		return this.loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	
	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public String getMiddlename() {
		return this.middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getChangepwd() {
		return this.changepwd;
	}

	public void setChangepwd(String changepwd) {
		this.changepwd = changepwd;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getUfirstname() {
		return this.ufirstname;
	}

	public void setUfirstname(String ufirstname) {
		this.ufirstname = ufirstname;
	}

	public String getSalutationname() {
		return this.salutationname;
	}

	public void setSalutationname(String salutationname) {
		this.salutationname = salutationname;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getInsgroupid() {
		return this.insgroupid;
	}

	public void setInsgroupid(int insgroupid) {
		this.insgroupid = insgroupid;
	}

	
	public int getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}

	public String getSuffixname() {
		return this.suffixname;
	}

	public void setSuffixname(String suffixname) {
		this.suffixname = suffixname;
	}

	public String getDbaname() {
		return this.dbaname;
	}

	public void setDbaname(String dbaname) {
		this.dbaname = dbaname;
	}

	public String getUlastname() {
		return this.ulastname;
	}

	public void setUlastname(String ulastname) {
		this.ulastname = ulastname;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getInsagencyid() {
		return this.insagencyid;
	}

	public void setInsagencyid(int insagencyid) {
		this.insagencyid = insagencyid;
	}

	public String getNametype() {
		return this.nametype;
	}

	public void setNametype(String nametype) {
		this.nametype = nametype;
	}

	
}
