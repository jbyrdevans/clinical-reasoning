package org.opencds.cqf.cql.evaluator.fhir.adapter.r4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.ICompositeType;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Attachment;
import org.hl7.fhir.r4.model.CanonicalType;
import org.hl7.fhir.r4.model.DataRequirement;
import org.hl7.fhir.r4.model.Library;
import org.opencds.cqf.cql.evaluator.fhir.util.DependencyInfo;
import org.opencds.cqf.cql.evaluator.fhir.visitor.KnowledgeArtifactVisitor;

class LibraryAdapter extends KnowledgeArtifactAdapter implements org.opencds.cqf.cql.evaluator.fhir.adapter.LibraryAdapter {

  private Library library;

  public LibraryAdapter(IBaseResource library) {
    super(library);

    if (!library.fhirType().equals("Library")) {
      throw new IllegalArgumentException(
          "resource passed as library argument is not a Library resource");
    }

    this.library = (Library) library;
  }

  public void accept(KnowledgeArtifactVisitor visitor) {
    visitor.visit(this);
  }

  protected Library getLibrary() {
    return this.library;
  }

  @Override
  public IBaseResource get() {
    return this.library;
  }

  @Override
  public IIdType getId() {
    return this.getLibrary().getIdElement();
  }

  @Override
  public void setId(IIdType id) {
    this.getLibrary().setId(id);
  }

  @Override
  public String getName() {
    return this.getLibrary().getName();
  }

  @Override
  public void setName(String name) {
    this.getLibrary().setName(name);
  }

  @Override
  public String getUrl() {
    return this.getLibrary().getUrl();
  }

  @Override
  public void setUrl(String url) {
    this.getLibrary().setUrl(url);
  }

  @Override
  public String getVersion() {
    return this.getLibrary().getVersion();
  }

  @Override
  public void setVersion(String version) {
    this.getLibrary().setVersion(version);
  }

  @Override
  public boolean hasContent() {
    return this.getLibrary().hasContent();
  }

  @Override
  public List<ICompositeType> getContent() {
    return this.getLibrary().getContent().stream().collect(Collectors.toList());
  }

  @Override
  public void setContent(List<ICompositeType> attachments) {
    List<Attachment> castAttachments =
        attachments.stream().map(x -> (Attachment) x).collect(Collectors.toList());
    this.getLibrary().setContent(castAttachments);
  }

  @Override
  public ICompositeType addContent() {
    return this.getLibrary().addContent();
  }

  @Override
  public List<DependencyInfo> getDependencies() {
    List<DependencyInfo> references = new ArrayList<>();

    // relatedArtifact[].resource
    references.addAll(getRelatedArtifactReferences(this.library, this.library.getRelatedArtifact()));

    // dataRequirement
    List<DataRequirement> dataRequirements = this.library.getDataRequirement();
    for (DataRequirement dr : dataRequirements) {
      // dataRequirement.profile[]
      List<CanonicalType> profiles = dr.getProfile();
      for (CanonicalType ct : profiles) {
        if (ct.hasValue()) {
          String referenceSource = this.library.getUrl();
          if (this.library.getVersion() != null && !this.getVersion().isEmpty()) {
            referenceSource = referenceSource + "|" + this.library.getVersion();
          }

          DependencyInfo dependency = new DependencyInfo(referenceSource, ct.getValue());
          references.add(dependency);
//					Bundle referencedResourceBundle = searchResourceByUrl(ct.getValue(), fhirDal);
//					MetadataResource referencedResource = KnowledgeArtifactAdapter.findLatestVersion(referencedResourceBundle);
//					if (referencedResource == null) {
//						throw new ResourceNotFoundException(String.format("Resource for Canonical '%s' not found.", ct.getValue()));
//					} else {
//						resources.addAll(internalPackage(referencedResource, fhirDal));
//					}
        }
      }

      // dataRequirement.codeFilter[].valueset
      List<DataRequirement.DataRequirementCodeFilterComponent> codeFilters = dr.getCodeFilter();
      for (DataRequirement.DataRequirementCodeFilterComponent cf : codeFilters) {
        if (cf.hasValueSet()) {
          String referenceSource = this.library.getUrl();
          if (this.library.getVersion() != null && !this.getVersion().isEmpty()) {
            referenceSource = referenceSource + "|" + this.library.getVersion();
          }

          DependencyInfo dependency = new DependencyInfo(referenceSource, cf.getValueSet());
          references.add(dependency);
//					Bundle referencedResourceBundle = searchResourceByUrl(cf.getValueSet(), fhirDal);
//					MetadataResource referencedResource = KnowledgeArtifactAdapter.findLatestVersion(referencedResourceBundle);
//					if (referencedResource == null) {
//						throw new ResourceNotFoundException(String.format("Resource for Canonical '%s' not found.", cf.getValueSet()));
//					} else {
//						resources.addAll(internalPackage(referencedResource, fhirDal));
//					}
        }
      }
    }
    return references;
  }
}
