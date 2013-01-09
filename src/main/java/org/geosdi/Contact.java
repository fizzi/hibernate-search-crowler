/*
 *  geo-platform
 *  Rich webgis framework
 *  http://geo-platform.org
 * ====================================================================
 *
 * Copyright (C) 2008-2012 geoSDI Group (CNR IMAA - Potenza - ITALY).
 *
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. This program is distributed in the 
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details. You should have received a copy of the GNU General 
 * Public License along with this program. If not, see http://www.gnu.org/licenses/ 
 *
 * ====================================================================
 *
 * Linking this library statically or dynamically with other modules is 
 * making a combined work based on this library. Thus, the terms and 
 * conditions of the GNU General Public License cover the whole combination. 
 * 
 * As a special exception, the copyright holders of this library give you permission 
 * to link this library with independent modules to produce an executable, regardless 
 * of the license terms of these independent modules, and to copy and distribute 
 * the resulting executable under terms of your choice, provided that you also meet, 
 * for each linked independent module, the terms and conditions of the license of 
 * that module. An independent module is a module which is not derived from or 
 * based on this library. If you modify this library, you may extend this exception 
 * to your version of the library, but you are not obligated to do so. If you do not 
 * wish to do so, delete this exception statement from your version. 
 *
 */
package org.geosdi;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.SnowballPorterFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

/**
 * @author Nazzareno Sileno - CNR IMAA geoSDI Group
 * @email nazzareno.sileno@geosdi.org
 */
@Entity
@AnalyzerDef(name = "customanalyzer",
tokenizer =
@TokenizerDef(factory = StandardTokenizerFactory.class),
filters = {
    @TokenFilterDef(factory = LowerCaseFilterFactory.class),
    @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
        @Parameter(name = "language", value = "Italian")
    })
})
@Indexed
public class Contact implements Serializable {

    private static final long serialVersionUID = 5127853786383020329L;
    //
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "CONTACT_SEQ")
    @SequenceGenerator(name = "CONTACT_SEQ", sequenceName = "CONTACT_SEQ")
    @DocumentId
    private Long id;
    //
    @Column(name = "category")
    private String category;
    //
    @Column(name = "business_name")
    private String businessName;
    //
    @Column(name = "address")
    private String address;
    //
    @Column(name = "city")
    private String city;
    //
    @Column(name = "state")
    private String state;
    //
    @Column(name = "post_code", length = 5)
    private String postCode;
    //
    @Column(name = "phone_numbers", columnDefinition = "TEXT")
    private String phoneNumbers;
    //
    @Column(name = "fax_numbers", columnDefinition = "TEXT")
    private String faxNumbers;
    //
    @Column(name = "website")
    private String website;
    //
    @Column(name = "email", columnDefinition = "TEXT")
    private String email;
    //
    @Column(name = "yellow_page_url")
    private String yellowPageURL;
    //
    @Column(name = "map_url")
    private String mapURL;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Field(store = Store.YES, analyze= Analyze.YES)
    @Analyzer(definition = "customanalyzer")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Field(store = Store.YES, analyze= Analyze.YES)
    @Analyzer(definition = "customanalyzer")
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getFaxNumbers() {
        return faxNumbers;
    }

    public void setFaxNumbers(String faxNumbers) {
        this.faxNumbers = faxNumbers;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getYellowPageURL() {
        return yellowPageURL;
    }

    public void setYellowPageURL(String yellowPageURL) {
        this.yellowPageURL = yellowPageURL;
    }

    public String getMapURL() {
        return mapURL;
    }

    public void setMapURL(String mapURL) {
        this.mapURL = mapURL;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Contact other = (Contact) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Contact{ " + "id = " + id + ", category = " + category
                + ", businessName = " + businessName + ", address = "
                + address + ", city = " + city + ", state = " + state
                + ", postCode = " + postCode + ", phoneNumbers = "
                + phoneNumbers + ", faxNumbers = " + faxNumbers
                + ", website = " + website + ", email = " + email
                + ", yellowPageURL = " + yellowPageURL + ", mapURL = "
                + mapURL + '}';
    }
}
