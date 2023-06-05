//Generated by the protocol buffer compiler. DO NOT EDIT!
// source: com/zenmen/easylog_su/proto/log.proto

package com.zenmen.easylog_su.proto.gen;

@kotlin.jvm.JvmName("-initializelogBatch")
public inline fun logBatch(block: com.zenmen.easylog_su.proto.gen.LogBatchKt.Dsl.() -> kotlin.Unit): com.zenmen.easylog_su.proto.gen.LogOuterClass.LogBatch =
  com.zenmen.easylog_su.proto.gen.LogBatchKt.Dsl._create(com.zenmen.easylog_su.proto.gen.LogOuterClass.LogBatch.newBuilder()).apply { block() }._build()
public object LogBatchKt {
  @kotlin.OptIn(com.google.protobuf.kotlin.OnlyForUseByGeneratedProtoCode::class)
  @com.google.protobuf.kotlin.ProtoDslMarker
  public class Dsl private constructor(
    private val _builder: com.zenmen.easylog_su.proto.gen.LogOuterClass.LogBatch.Builder
  ) {
    public companion object {
      @kotlin.jvm.JvmSynthetic
      @kotlin.PublishedApi
      internal fun _create(builder: com.zenmen.easylog_su.proto.gen.LogOuterClass.LogBatch.Builder): Dsl = Dsl(builder)
    }

    @kotlin.jvm.JvmSynthetic
    @kotlin.PublishedApi
    internal fun _build(): com.zenmen.easylog_su.proto.gen.LogOuterClass.LogBatch = _builder.build()

    /**
     * An uninstantiable, behaviorless type to represent the field in
     * generics.
     */
    @kotlin.OptIn(com.google.protobuf.kotlin.OnlyForUseByGeneratedProtoCode::class)
    public class LogProxy private constructor() : com.google.protobuf.kotlin.DslProxy()
    /**
     * <code>repeated .com.zenmen.easylog_su.proto.gen.Log log = 1;</code>
     */
     public val log: com.google.protobuf.kotlin.DslList<com.zenmen.easylog_su.proto.gen.LogOuterClass.Log, LogProxy>
      @kotlin.jvm.JvmSynthetic
      get() = com.google.protobuf.kotlin.DslList(
        _builder.getLogList()
      )
    /**
     * <code>repeated .com.zenmen.easylog_su.proto.gen.Log log = 1;</code>
     * @param value The log to add.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("addLog")
    public fun com.google.protobuf.kotlin.DslList<com.zenmen.easylog_su.proto.gen.LogOuterClass.Log, LogProxy>.add(value: com.zenmen.easylog_su.proto.gen.LogOuterClass.Log) {
      _builder.addLog(value)
    }
    /**
     * <code>repeated .com.zenmen.easylog_su.proto.gen.Log log = 1;</code>
     * @param value The log to add.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("plusAssignLog")
    @Suppress("NOTHING_TO_INLINE")
    public inline operator fun com.google.protobuf.kotlin.DslList<com.zenmen.easylog_su.proto.gen.LogOuterClass.Log, LogProxy>.plusAssign(value: com.zenmen.easylog_su.proto.gen.LogOuterClass.Log) {
      add(value)
    }
    /**
     * <code>repeated .com.zenmen.easylog_su.proto.gen.Log log = 1;</code>
     * @param values The log to add.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("addAllLog")
    public fun com.google.protobuf.kotlin.DslList<com.zenmen.easylog_su.proto.gen.LogOuterClass.Log, LogProxy>.addAll(values: kotlin.collections.Iterable<com.zenmen.easylog_su.proto.gen.LogOuterClass.Log>) {
      _builder.addAllLog(values)
    }
    /**
     * <code>repeated .com.zenmen.easylog_su.proto.gen.Log log = 1;</code>
     * @param values The log to add.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("plusAssignAllLog")
    @Suppress("NOTHING_TO_INLINE")
    public inline operator fun com.google.protobuf.kotlin.DslList<com.zenmen.easylog_su.proto.gen.LogOuterClass.Log, LogProxy>.plusAssign(values: kotlin.collections.Iterable<com.zenmen.easylog_su.proto.gen.LogOuterClass.Log>) {
      addAll(values)
    }
    /**
     * <code>repeated .com.zenmen.easylog_su.proto.gen.Log log = 1;</code>
     * @param index The index to set the value at.
     * @param value The log to set.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("setLog")
    public operator fun com.google.protobuf.kotlin.DslList<com.zenmen.easylog_su.proto.gen.LogOuterClass.Log, LogProxy>.set(index: kotlin.Int, value: com.zenmen.easylog_su.proto.gen.LogOuterClass.Log) {
      _builder.setLog(index, value)
    }
    /**
     * <code>repeated .com.zenmen.easylog_su.proto.gen.Log log = 1;</code>
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("clearLog")
    public fun com.google.protobuf.kotlin.DslList<com.zenmen.easylog_su.proto.gen.LogOuterClass.Log, LogProxy>.clear() {
      _builder.clearLog()
    }

  }
}
@kotlin.jvm.JvmSynthetic
public inline fun com.zenmen.easylog_su.proto.gen.LogOuterClass.LogBatch.copy(block: com.zenmen.easylog_su.proto.gen.LogBatchKt.Dsl.() -> kotlin.Unit): com.zenmen.easylog_su.proto.gen.LogOuterClass.LogBatch =
  com.zenmen.easylog_su.proto.gen.LogBatchKt.Dsl._create(this.toBuilder()).apply { block() }._build()
