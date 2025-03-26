package dk.alexandra.fresco.bgw.datatypes;

import dk.alexandra.fresco.framework.builder.numeric.field.FieldElement;
import dk.alexandra.fresco.framework.network.serializers.ByteSerializer;
import dk.alexandra.fresco.framework.value.SInt;
import java.io.Serializable;

public class BGWSInt implements SInt, Serializable {

  private final FieldElement share;

  public BGWSInt(FieldElement share) {
    this.share = share;
  }

  public FieldElement getShare() {
    return share;
  }

  public SInt out() {
    return this;
  }

  public BGWSInt add(BGWSInt other) {
    return new BGWSInt(share.add(other.share));
  }

  public BGWSInt add(FieldElement other, int id) {
    return new BGWSInt(share.add(other));
  }

  public BGWSInt subtract(BGWSInt other) {
    return new BGWSInt(share.subtract(other.share));
  }

  public BGWSInt subtract(FieldElement other, int id) {
    return new BGWSInt(share.subtract(other));
  }

  public BGWSInt multiply(FieldElement other) {
    return new BGWSInt(share.multiply(other));
  }

  public byte[] serializeShare(ByteSerializer<FieldElement> serializer) {
    return serializer.serialize(getShare());
  }

}
