package net.freedom.gj.mapper.config


trait Converter {
  def convert(subject: AnyRef): AnyRef
}

class ToStringConverter extends Converter {

  def convert(source: AnyRef): AnyRef = {
    if (source == null) {
      return new String("")
    }
    source.toString
  }
}