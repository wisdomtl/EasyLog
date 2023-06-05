//Generated by the protocol buffer compiler. DO NOT EDIT!
// source: com/taylor/demo/protobuf/ad_log.proto

package com.taylor.demo.protobuf.gen;

@kotlin.jvm.JvmName("-initializeeventBatch")
public inline fun eventBatch(block: com.taylor.demo.protobuf.gen.EventBatchKt.Dsl.() -> kotlin.Unit): com.taylor.demo.protobuf.gen.AdLog.EventBatch =
  com.taylor.demo.protobuf.gen.EventBatchKt.Dsl._create(com.taylor.demo.protobuf.gen.AdLog.EventBatch.newBuilder()).apply { block() }._build()
public object EventBatchKt {
  @kotlin.OptIn(com.google.protobuf.kotlin.OnlyForUseByGeneratedProtoCode::class)
  @com.google.protobuf.kotlin.ProtoDslMarker
  public class Dsl private constructor(
    private val _builder: com.taylor.demo.protobuf.gen.AdLog.EventBatch.Builder
  ) {
    public companion object {
      @kotlin.jvm.JvmSynthetic
      @kotlin.PublishedApi
      internal fun _create(builder: com.taylor.demo.protobuf.gen.AdLog.EventBatch.Builder): Dsl = Dsl(builder)
    }

    @kotlin.jvm.JvmSynthetic
    @kotlin.PublishedApi
    internal fun _build(): com.taylor.demo.protobuf.gen.AdLog.EventBatch = _builder.build()

    /**
     * An uninstantiable, behaviorless type to represent the field in
     * generics.
     */
    @kotlin.OptIn(com.google.protobuf.kotlin.OnlyForUseByGeneratedProtoCode::class)
    public class EventsProxy private constructor() : com.google.protobuf.kotlin.DslProxy()
    /**
     * <code>repeated .com.taylor.demo.protobuf.gen.Event events = 1;</code>
     */
     public val events: com.google.protobuf.kotlin.DslList<com.taylor.demo.protobuf.gen.AdLog.Event, EventsProxy>
      @kotlin.jvm.JvmSynthetic
      get() = com.google.protobuf.kotlin.DslList(
        _builder.getEventsList()
      )
    /**
     * <code>repeated .com.taylor.demo.protobuf.gen.Event events = 1;</code>
     * @param value The events to add.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("addEvents")
    public fun com.google.protobuf.kotlin.DslList<com.taylor.demo.protobuf.gen.AdLog.Event, EventsProxy>.add(value: com.taylor.demo.protobuf.gen.AdLog.Event) {
      _builder.addEvents(value)
    }
    /**
     * <code>repeated .com.taylor.demo.protobuf.gen.Event events = 1;</code>
     * @param value The events to add.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("plusAssignEvents")
    @Suppress("NOTHING_TO_INLINE")
    public inline operator fun com.google.protobuf.kotlin.DslList<com.taylor.demo.protobuf.gen.AdLog.Event, EventsProxy>.plusAssign(value: com.taylor.demo.protobuf.gen.AdLog.Event) {
      add(value)
    }
    /**
     * <code>repeated .com.taylor.demo.protobuf.gen.Event events = 1;</code>
     * @param values The events to add.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("addAllEvents")
    public fun com.google.protobuf.kotlin.DslList<com.taylor.demo.protobuf.gen.AdLog.Event, EventsProxy>.addAll(values: kotlin.collections.Iterable<com.taylor.demo.protobuf.gen.AdLog.Event>) {
      _builder.addAllEvents(values)
    }
    /**
     * <code>repeated .com.taylor.demo.protobuf.gen.Event events = 1;</code>
     * @param values The events to add.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("plusAssignAllEvents")
    @Suppress("NOTHING_TO_INLINE")
    public inline operator fun com.google.protobuf.kotlin.DslList<com.taylor.demo.protobuf.gen.AdLog.Event, EventsProxy>.plusAssign(values: kotlin.collections.Iterable<com.taylor.demo.protobuf.gen.AdLog.Event>) {
      addAll(values)
    }
    /**
     * <code>repeated .com.taylor.demo.protobuf.gen.Event events = 1;</code>
     * @param index The index to set the value at.
     * @param value The events to set.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("setEvents")
    public operator fun com.google.protobuf.kotlin.DslList<com.taylor.demo.protobuf.gen.AdLog.Event, EventsProxy>.set(index: kotlin.Int, value: com.taylor.demo.protobuf.gen.AdLog.Event) {
      _builder.setEvents(index, value)
    }
    /**
     * <code>repeated .com.taylor.demo.protobuf.gen.Event events = 1;</code>
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("clearEvents")
    public fun com.google.protobuf.kotlin.DslList<com.taylor.demo.protobuf.gen.AdLog.Event, EventsProxy>.clear() {
      _builder.clearEvents()
    }

  }
}
@kotlin.jvm.JvmSynthetic
public inline fun com.taylor.demo.protobuf.gen.AdLog.EventBatch.copy(block: com.taylor.demo.protobuf.gen.EventBatchKt.Dsl.() -> kotlin.Unit): com.taylor.demo.protobuf.gen.AdLog.EventBatch =
  com.taylor.demo.protobuf.gen.EventBatchKt.Dsl._create(this.toBuilder()).apply { block() }._build()

