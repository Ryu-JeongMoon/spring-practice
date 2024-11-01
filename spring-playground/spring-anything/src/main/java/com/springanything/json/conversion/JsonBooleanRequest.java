package com.springanything.json.conversion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class JsonBooleanRequest {

  @JsonProperty("use_station_shared_drive")
  private boolean useStationSharedDrive;

  @JsonProperty("use_station_shared_drive_in_number")
  @JsonFormat(shape = JsonFormat.Shape.NUMBER)
  private boolean useStationSharedDriveInNumber;

}
