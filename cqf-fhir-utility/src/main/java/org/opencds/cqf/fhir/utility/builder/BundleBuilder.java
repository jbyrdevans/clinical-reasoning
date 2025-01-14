package org.opencds.cqf.fhir.utility.builder;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Date;
import org.hl7.fhir.instance.model.api.IBaseBundle;

public class BundleBuilder<T extends IBaseBundle> extends ResourceBuilder<BundleBuilder<T>, T> {

    private String myType;

    private Date myTimestamp = new Date();

    public BundleBuilder(Class<T> theResourceClass) {
        super(theResourceClass);
    }

    public BundleBuilder(Class<T> theResourceClass, String theId) {
        super(theResourceClass, theId);
    }

    public BundleBuilder(Class<T> theResourceClass, String theId, String theType) {
        this(theResourceClass, theId);
        checkNotNull(theType);

        myType = theType;
    }

    public BundleBuilder<T> withType(String theType) {
        checkNotNull(theType);

        myType = theType;

        return this;
    }

    public BundleBuilder<T> withTimestamp(Date theTimestamp) {
        myTimestamp = theTimestamp;

        return this;
    }

    @Override
    public T build() {
        checkNotNull(myType);

        return super.build();
    }

    @Override
    protected void initializeDstu3(T theResource) {
        super.initializeDstu3(theResource);
        org.hl7.fhir.dstu3.model.Bundle bundle = (org.hl7.fhir.dstu3.model.Bundle) theResource;

        bundle.setType(org.hl7.fhir.dstu3.model.Bundle.BundleType.valueOf(myType));

        bundle.setIdentifier(new org.hl7.fhir.dstu3.model.Identifier()
                .setSystem(getIdentifier().getKey())
                .setValue(getIdentifier().getValue()));
    }

    @Override
    protected void initializeR4(T theResource) {
        super.initializeR4(theResource);
        org.hl7.fhir.r4.model.Bundle bundle = (org.hl7.fhir.r4.model.Bundle) theResource;

        bundle.setType(org.hl7.fhir.r4.model.Bundle.BundleType.valueOf(myType));

        bundle.setIdentifier(new org.hl7.fhir.r4.model.Identifier()
                .setSystem(getIdentifier().getKey())
                .setValue(getIdentifier().getValue()));

        bundle.setTimestamp(myTimestamp);
    }

    @Override
    protected void initializeR5(T theResource) {
        super.initializeR5(theResource);
        org.hl7.fhir.r5.model.Bundle bundle = (org.hl7.fhir.r5.model.Bundle) theResource;

        bundle.setType(org.hl7.fhir.r5.model.Bundle.BundleType.valueOf(myType));

        bundle.setIdentifier(new org.hl7.fhir.r5.model.Identifier()
                .setSystem(getIdentifier().getKey())
                .setValue(getIdentifier().getValue()));

        bundle.setTimestamp(myTimestamp);
    }
}
